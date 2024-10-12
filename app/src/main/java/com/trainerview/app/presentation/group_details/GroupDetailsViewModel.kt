package com.trainerview.app.presentation.group_details

import com.trainerview.app.base.BaseViewModel
import com.trainerview.app.base.NavigateTo
import com.trainerview.app.data.db.model.TrainingDb
import com.trainerview.app.data.db.model.TrainingVisitDb
import com.trainerview.app.date.DateFormatter
import com.trainerview.app.domain.GroupRepository
import com.trainerview.app.domain.ParticipantRepository
import com.trainerview.app.domain.TrainingRepository
import com.trainerview.app.presentation.update_training.ParticipantItem
import com.trainerview.app.presentation.update_training.UpdateTrainingNavParams
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

    fun showCreateTrainingScreen() {
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