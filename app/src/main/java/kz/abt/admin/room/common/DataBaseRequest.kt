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
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kz.abt.admin.application.App
import kz.abt.admin.room.table.*

object DataBaseRequest {
    private val dataBase = App.getInstance()?.getDataBase()

    interface DataBaseListener<in T> {

        fun insert(item: T)
    }

    fun insertComplete(complete: Complete, dataBaseListener: DataBaseListener<Complete>) {

        Completable.fromAction { dataBase!!.completeDao().insert(complete) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(insertCompletable(complete, dataBaseListener))
    }

    fun insertGame(game: Game, dataBaseListener: DataBaseListener<Game>) {

        Completable.fromAction { dataBase!!.gameDao().insert(game) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(insertCompletable(game, dataBaseListener))
    }

    fun insertPlayers(players: Players, dataBaseListener: DataBaseListener<Players>) {

        Completable.fromAction { dataBase!!.playersDao().insert(players) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(insertCompletable(players, dataBaseListener))
    }

    fun insertTeam(team: Team, dataBaseListener: DataBaseListener<Team>) {

        Completable.fromAction { dataBase!!.teamDao().insert(team) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(insertCompletable(team, dataBaseListener))
    }

    fun insertTournament(tournament: Tournament, dataBaseListener: DataBaseListener<Tournament>) {

        Completable.fromAction { dataBase!!.tournamentDao().insert(tournament) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(insertCompletable(tournament, dataBaseListener))
    }

    fun getCompleteList(idTournament: Int) = dataBase!!.completeDao()
            .getCompleteList(idTournament)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getGameList(idTournament: Int) = dataBase!!.gameDao()
            .getGameList(idTournament)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getPlayers(idTeam: Int) = dataBase!!.playersDao()
            .getPlayers(idTeam)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getTeamList(idTournament: Int) = dataBase!!.teamDao()
            .getTeamList(idTournament)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getTournamentList() = dataBase!!.tournamentDao()
            .getTournamentList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    private fun <T> insertCompletable(
            item: T,
            dataBaseListener: DataBaseListener<T>
    ): CompletableObserver = object : CompletableObserver {
        override fun onComplete() {
            dataBaseListener.insert(item)
        }

        override fun onSubscribe(d: Disposable) {

        }

        override fun onError(e: Throwable) {

        }
    }
}