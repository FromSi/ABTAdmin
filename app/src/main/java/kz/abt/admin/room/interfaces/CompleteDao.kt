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

package kz.abt.admin.room.interfaces

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.reactivex.Maybe
import kz.abt.admin.room.table.Complete

@Dao
interface CompleteDao {

    @Insert
    fun insert(complete: Complete)

    @Query("SELECT * FROM `complete` WHERE `complete`.idTournament = :idTournament")
    fun getCompleteList(idTournament: Int): Maybe<MutableList<Complete>>
}