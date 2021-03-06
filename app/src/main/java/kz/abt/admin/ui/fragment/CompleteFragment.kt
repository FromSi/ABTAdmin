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

package kz.abt.admin.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.fragment_complete.*
import kz.abt.admin.R
import kz.abt.admin.mvp.presenter.fragment.CompletePresenter
import kz.abt.admin.mvp.view.fragment.CompleteView
import kz.abt.admin.ui.adapters.CompleteAdapter
import kz.abt.admin.ui.util.CompleteJSON

class CompleteFragment : MvpAppCompatFragment(), CompleteView {
    @InjectPresenter
    lateinit var presenter: CompletePresenter
    private lateinit var adapter: CompleteAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_complete, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initList()
        this.arguments.apply {

            if (this != null)
                presenter.initPresenter(getInt("idTournament"))
        }
    }

    @SuppressLint("SetTextI18n")
    override fun setList(list: MutableList<CompleteJSON>) {
        value.text = "Всего законченных игр: ${list.size}"

        adapter.setList(list)
    }

    private fun initList() {

        LinearLayoutManager(context).apply {
            orientation = LinearLayoutManager.VERTICAL
            list.layoutManager = this
        }

        adapter = CompleteAdapter().apply {
            list.adapter = this
        }
    }
}