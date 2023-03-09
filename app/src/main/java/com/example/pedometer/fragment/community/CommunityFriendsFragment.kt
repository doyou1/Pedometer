package com.example.pedometer.fragment.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pedometer.adapter.community.CommunityFriendsAdapter
import com.example.pedometer.databinding.FragmentCommunityFriendsBinding
import com.example.pedometer.domain.CommunityFriends
import com.example.pedometer.util.*
import com.github.mikephil.charting.data.*

class CommunityFriendsFragment : Fragment() {

    private var _binding: FragmentCommunityFriendsBinding? = null
    private val binding get() = _binding!!
    private val TAG = this::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCommunityFriendsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val list = listOf(
            CommunityFriends(
                1,
                "https://ibighit.com/bts/images/profile/proof/member/member-rm.jpg",
                "Jennifer Dunst",
                "9876",
                "2023/03/09 21:41"
            ),
            CommunityFriends(
                2,
                "https://ibighit.com/bts/images/profile/proof/member/member-jimin.jpg",
                "John Doe",
                "5432",
                "2023/03/09 21:41"
            ),
            CommunityFriends(
                3,
                "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FIuloj%2Fbtq98uDPwBx%2F1ManmKCAylybcg5Q3zno40%2Fimg.jpg",
                "Lianna Afara",
                "19876",
                "2023/03/09 21:41"
            ),
            CommunityFriends(
                4,
                "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FkL6aZ%2Fbtq93nFYWq1%2Ff6vKYZNPs0ZdKpQIZIDMsK%2Fimg.jpg",
                "Don Carons",
                "5432",
                "2023/03/09 21:41"
            ),
            CommunityFriends(
                5,
                "https://ibighit.com/bts/images/profile/proof/member/member-rm.jpg",
                "Lilly Aliana",
                "1987",
                "2023/03/09 21:41"
            ),
            CommunityFriends(
                6,
                "https://ibighit.com/bts/images/profile/proof/member/member-jimin.jpg",
                "Sim JH",
                "65432",
                "2023/03/09 21:41"
            ),
            CommunityFriends(
                7,
                "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FIuloj%2Fbtq98uDPwBx%2F1ManmKCAylybcg5Q3zno40%2Fimg.jpg",
                "aki chu",
                "65432",
                "2023/03/09 21:41"
            ),
            CommunityFriends(
                8,
                "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FkL6aZ%2Fbtq93nFYWq1%2Ff6vKYZNPs0ZdKpQIZIDMsK%2Fimg.jpg",
                "what's your name?",
                "65432",
                "2023/03/09 21:41"
            ),
            CommunityFriends(
                8,
                "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FkL6aZ%2Fbtq93nFYWq1%2Ff6vKYZNPs0ZdKpQIZIDMsK%2Fimg.jpg",
                "what's your name?",
                "65432",
                "2023/03/09 21:41"
            ),
            CommunityFriends(
                8,
                "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FkL6aZ%2Fbtq93nFYWq1%2Ff6vKYZNPs0ZdKpQIZIDMsK%2Fimg.jpg",
                "what's your name?",
                "65432",
                "2023/03/09 21:41"
            ),
            CommunityFriends(
                8,
                "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FkL6aZ%2Fbtq93nFYWq1%2Ff6vKYZNPs0ZdKpQIZIDMsK%2Fimg.jpg",
                "what's your name?",
                "65432",
                "2023/03/09 21:41"
            ),
            CommunityFriends(
                8,
                "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FkL6aZ%2Fbtq93nFYWq1%2Ff6vKYZNPs0ZdKpQIZIDMsK%2Fimg.jpg",
                "what's your name?",
                "65432",
                "2023/03/09 21:41"
            ),
            CommunityFriends(
                8,
                "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FkL6aZ%2Fbtq93nFYWq1%2Ff6vKYZNPs0ZdKpQIZIDMsK%2Fimg.jpg",
                "what's your name?",
                "65432",
                "2023/03/09 21:41"
            ),
            CommunityFriends(
                8,
                "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FkL6aZ%2Fbtq93nFYWq1%2Ff6vKYZNPs0ZdKpQIZIDMsK%2Fimg.jpg",
                "what's your name?",
                "65432",
                "2023/03/09 21:41"
            ),
            CommunityFriends(
                8,
                "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FkL6aZ%2Fbtq93nFYWq1%2Ff6vKYZNPs0ZdKpQIZIDMsK%2Fimg.jpg",
                "what's your name?",
                "65432",
                "2023/03/09 21:41"
            ),
            CommunityFriends(
                8,
                "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FkL6aZ%2Fbtq93nFYWq1%2Ff6vKYZNPs0ZdKpQIZIDMsK%2Fimg.jpg",
                "what's your name?",
                "65432",
                "2023/03/09 21:41"
            ),
            CommunityFriends(
                8,
                "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FkL6aZ%2Fbtq93nFYWq1%2Ff6vKYZNPs0ZdKpQIZIDMsK%2Fimg.jpg",
                "what's your name?",
                "65432",
                "2023/03/09 21:41"
            ),
            CommunityFriends(
                8,
                "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FkL6aZ%2Fbtq93nFYWq1%2Ff6vKYZNPs0ZdKpQIZIDMsK%2Fimg.jpg",
                "what's your name?",
                "65432",
                "2023/03/09 21:41"
            ),
        )

        val dividerItemDecoration = DividerItemDecoration(
            requireContext(),
            LinearLayoutManager(requireContext()).orientation
        )
        binding.recyclerView.addItemDecoration(dividerItemDecoration)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = CommunityFriendsAdapter(list)
    }

    override fun onResume() {
        super.onResume()

    }

    companion object {
        private var instance: CommunityFriendsFragment? = null
        fun getInstance(): CommunityFriendsFragment {
            if (instance == null) {
                instance = CommunityFriendsFragment()
            }
            return instance!!
        }
    }
}