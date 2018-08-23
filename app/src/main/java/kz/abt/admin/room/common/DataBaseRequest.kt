/*
 * Copyright 2018 Vlad Weber-Pflaumer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package kz.abt.admin.room.common

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kz.abt.admin.application.App
import kz.abt.admin.room.table.*
import kz.abt.admin.ui.util.TeamJSON

object DataBaseRequest {
    private val dataBase = App.getInstance()?.getDataBase()

    fun insertComplete(complete: Complete) {

        Completable.fromAction { dataBase!!.completeDao().insert(complete) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    fun insertGame(game: Game) {

        Completable.fromAction { dataBase!!.gameDao().insert(game) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    fun insertTeam(idTournament: Int, teamJSON: TeamJSON) {

        Completable
                .fromAction {
                    dataBase!!.playersDao().insert(
                            Players(
                                    dataBase.teamDao()
                                            .insert(Team(idTournament, teamJSON.title, 0)),
                                    teamJSON.p1,
                                    teamJSON.p2,
                                    teamJSON.p3,
                                    teamJSON.p4,
                                    teamJSON.p5
                            )
                    )
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    fun insertTournament(tournament: List<Tournament>) {

        Completable.fromAction { dataBase!!.tournamentDao().insert(tournament) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    fun getCompleteList(idTournament: Int) = dataBase!!.completeDao()
            .getCompleteList(idTournament)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getGameList(idTournament: Int): Flowable<MutableList<Game>> = dataBase!!.gameDao()
            .getGameList(idTournament)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getPlayers(idTeam: Int): Maybe<Players> = dataBase!!.playersDao()
            .getPlayers(idTeam)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getTeamList(idTournament: Int): Flowable<MutableList<Team>> = dataBase!!.teamDao()
            .getTeamList(idTournament)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getTeam(id: Int): Maybe<Team> = dataBase!!.teamDao()
            .getTeam(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getTeamListMaybe(idTournament: Int): Maybe<MutableList<Team>> = dataBase!!.teamDao()
            .getTeamListMaybe(idTournament)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getTournamentList(): Flowable<MutableList<Tournament>> = dataBase!!.tournamentDao()
            .getTournamentList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

}