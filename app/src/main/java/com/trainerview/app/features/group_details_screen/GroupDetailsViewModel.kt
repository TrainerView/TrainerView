package com.trainerview.app.features.group_details_screen

import com.trainerview.app.base.ui.BaseViewModel
import com.trainerview.app.base.ui.NavigateTo
import com.trainerview.app.features.training_domain.data.model.TrainingDb
import com.trainerview.app.features.training_domain.data.model.TrainingVisitDb
import com.trainerview.app.utils.DateFormatter
import com.trainerview.app.features.group_domain.GroupRepository
import com.trainerview.app.features.participant_domain.ParticipantRepository
import com.trainerview.app.features.training_domain.TrainingRepository
import com.trainerview.app.features.update_training_screen.ParticipantItem
import com.trainerview.app.features.update_training_screen.UpdateTrainingNavParams
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

class GroupDetailsViewModel @Inject constructor(
    private val groupRepository: GroupRepository,
    private val participantRepository: ParticipantRepository,
    private val trainingRepository: TrainingRepository
) : BaseViewModel() {

    private val args by navArgs<GroupDetailsFragmentArgs>()

    val adapter = TrainingAdapter()

    init {
        adapter.onItemClickListener = {
            postNavEvents(
                NavigateTo(
                    GroupDetailsFragmentDirections.actionToUpdateTrainingFragment(
                        UpdateTrainingNavParams.UpdateTraining(
                            trainingId = it.id,
                            date = DateFormatter.parseDate(it.date)!!,
                        )
                    )
                )
            )
        }
    }

    fun load() {
        safeLaunch {
            val trainings = trainingRepository.getTrainings(args.groupId)
            adapter.update(
                trainings.map {
                    TrainingListItem(
                        id = it.id,
                        date = it.date
                    )
                }
            )
        }
    }

    fun onCreateTrainingClick() {
        safeLaunch {
            val participants = withContext(Dispatchers.IO) {
                participantRepository.getParticipants(args.groupId)
            }.map {
                ParticipantItem(
                    id = it.id,
                    name = it.name
                )
            }
            postNavEvents(
                NavigateTo(
                    GroupDetailsFragmentDirections.actionToUpdateTrainingFragment(
                        UpdateTrainingNavParams.CreateTraining(participants)
                    )
                )
            )
        }
    }

    fun createTraining(
        date: Date,
        visitedParticipants: List<ParticipantItem>,
        missedParticipants: List<ParticipantItem>
    ) {

        safeLaunch {

            val trainingDb = TrainingDb(
                groupId = args.groupId,
                date = DateFormatter.formatString(date)
            )
            val trainingId = trainingRepository.insertTraining(trainingDb)

            visitedParticipants.forEach {
                val visit = TrainingVisitDb(
                    trainingId = trainingId,
                    participantId = it.id,
                    isVisited = true
                )
                trainingRepository.insertVisit(visit)
            }
            missedParticipants.forEach {
                val visit = TrainingVisitDb(
                    trainingId = trainingId,
                    participantId = it.id,
                    isVisited = false
                )
                trainingRepository.insertVisit(visit)
            }

            val trainings = trainingRepository.getTrainings(args.groupId)
            adapter.update(
                trainings.map {
                    TrainingListItem(
                        id = it.id,
                        date = it.date
                    )
                }
            )
        }
    }

    fun updateTrainingVisits(
        trainingId: Long,
        date: Date,
        visitedParticipants: List<ParticipantItem>,
        missedParticipants: List<ParticipantItem>
    ) {

        safeLaunch {

            val trainingDb = TrainingDb(
                id = trainingId,
                groupId = args.groupId,
                date = DateFormatter.formatString(date)
            )
            trainingRepository.updateTraining(trainingDb)
            trainingRepository.deleteAllVisits(trainingId)

            visitedParticipants.forEach {
                val visit = TrainingVisitDb(
                    trainingId = trainingId,
                    participantId = it.id,
                    isVisited = true
                )
                trainingRepository.insertVisit(visit)
            }
            missedParticipants.forEach {
                val visit = TrainingVisitDb(
                    trainingId = trainingId,
                    participantId = it.id,
                    isVisited = false
                )
                trainingRepository.insertVisit(visit)
            }

            val trainings = trainingRepository.getTrainings(args.groupId)
            adapter.update(
                trainings.map {
                    TrainingListItem(
                        id = it.id,
                        date = it.date
                    )
                }
            )
        }
    }
}