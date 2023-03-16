import com.example.pedometer.domain.AddFriends
import com.example.pedometer.domain.CommunityFriends
import com.example.pedometer.domain.CommunityNotifications
import com.example.pedometer.util.FLAG_STATUS_ADD
import com.example.pedometer.util.FLAG_STATUS_ALREADY
import com.example.pedometer.util.FLAG_STATUS_PENDING

val TEMP_LIST_COMMUNITY_FRIENDS = listOf(
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

val TEMP_TODAY_LIST_COMMUNITY_NOTIFICATIONS = listOf(
    CommunityNotifications(
        1,
        true,
        "https://ibighit.com/bts/images/profile/proof/member/member-rm.jpg",
        "Jennifer Dunst",
        "achieve goal!",
        "2023/03/09 21:41"
    ),
    CommunityNotifications(
        2,
        true,
        "https://ibighit.com/bts/images/profile/proof/member/member-jimin.jpg",
        "John Doe",
        "achieve goal!",
        "2023/03/09 21:41",
    ),
    CommunityNotifications(
        3,
        true,
        "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FIuloj%2Fbtq98uDPwBx%2F1ManmKCAylybcg5Q3zno40%2Fimg.jpg",
        "Lianna Afara",
        "achieve goal!",
        "2023/03/09 21:41"
    ),
    CommunityNotifications(
        4,
        true,
        "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FkL6aZ%2Fbtq93nFYWq1%2Ff6vKYZNPs0ZdKpQIZIDMsK%2Fimg.jpg",
        "Don Carons",
        "haven't achieved my goal in the last week",
        "2023/03/09 21:41"
    ),
    CommunityNotifications(
        5,
        true,
        "https://ibighit.com/bts/images/profile/proof/member/member-rm.jpg",
        "Lianna Afara",
        "achieve goal!",
        "2023/03/09 21:41"
    ),
    CommunityNotifications(
        5,
        true,
        "https://ibighit.com/bts/images/profile/proof/member/member-rm.jpg",
        "Lianna Afara",
        "achieve goal!",
        "2023/03/09 21:41"
    ),
    CommunityNotifications(
        5,
        true,
        "https://ibighit.com/bts/images/profile/proof/member/member-rm.jpg",
        "Lianna Afara",
        "achieve goal!",
        "2023/03/09 21:41"
    ),
    CommunityNotifications(
        5,
        true,
        "https://ibighit.com/bts/images/profile/proof/member/member-rm.jpg",
        "Lianna Afara",
        "achieve goal!",
        "2023/03/09 21:41"
    ),
    CommunityNotifications(
        5,
        true,
        "https://ibighit.com/bts/images/profile/proof/member/member-rm.jpg",
        "Lianna Afara",
        "achieve goal!",
        "2023/03/09 21:41"
    ),
)

val TEMP_THIS_WEEK_LIST_COMMUNITY_NOTIFICATIONS = listOf(
    CommunityNotifications(
        6,
        false,
        "https://ibighit.com/bts/images/profile/proof/member/member-jimin.jpg",
        "SIM JH",
        "haven't achieved my goal in the last week",
        "2023/03/09 21:41"
    ),
    CommunityNotifications(
        7,
        false,
        "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FIuloj%2Fbtq98uDPwBx%2F1ManmKCAylybcg5Q3zno40%2Fimg.jpg",
        "aki chu",
        "haven't achieved my goal in the last week",
        "2023/03/09 21:41"
    ),
    CommunityNotifications(
        8,
        false,
        "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FkL6aZ%2Fbtq93nFYWq1%2Ff6vKYZNPs0ZdKpQIZIDMsK%2Fimg.jpg",
        "what's your name?",
        "achieve goal!",
        "2023/03/09 21:41"
    ),
    CommunityNotifications(
        6,
        false,
        "https://ibighit.com/bts/images/profile/proof/member/member-jimin.jpg",
        "SIM JH",
        "haven't achieved my goal in the last week",
        "2023/03/09 21:41"
    ),
    CommunityNotifications(
        7,
        false,
        "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FIuloj%2Fbtq98uDPwBx%2F1ManmKCAylybcg5Q3zno40%2Fimg.jpg",
        "aki chu",
        "haven't achieved my goal in the last week",
        "2023/03/09 21:41"
    ),
    CommunityNotifications(
        8,
        false,
        "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FkL6aZ%2Fbtq93nFYWq1%2Ff6vKYZNPs0ZdKpQIZIDMsK%2Fimg.jpg",
        "what's your name?",
        "achieve goal!",
        "2023/03/09 21:41"
    ),
    CommunityNotifications(
        6,
        false,
        "https://ibighit.com/bts/images/profile/proof/member/member-jimin.jpg",
        "SIM JH",
        "haven't achieved my goal in the last week",
        "2023/03/09 21:41"
    ),
    CommunityNotifications(
        7,
        false,
        "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FIuloj%2Fbtq98uDPwBx%2F1ManmKCAylybcg5Q3zno40%2Fimg.jpg",
        "aki chu",
        "haven't achieved my goal in the last week",
        "2023/03/09 21:41"
    ),
    CommunityNotifications(
        8,
        false,
        "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FkL6aZ%2Fbtq93nFYWq1%2Ff6vKYZNPs0ZdKpQIZIDMsK%2Fimg.jpg",
        "what's your name?",
        "achieve goal!",
        "2023/03/09 21:41"
    ),
)

val TEMP_LIST_ADD_FRIENDS = listOf(
    AddFriends(
        1,
        "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FkL6aZ%2Fbtq93nFYWq1%2Ff6vKYZNPs0ZdKpQIZIDMsK%2Fimg.jpg",
        "Sim jh",
        FLAG_STATUS_ADD,
        "3000",
        "#123123"
    ),
    AddFriends(
        2,
        "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FIuloj%2Fbtq98uDPwBx%2F1ManmKCAylybcg5Q3zno40%2Fimg.jpg",
        "Aki chu",
        FLAG_STATUS_ADD,
        "4000",
        "#321321"
    ),
    AddFriends(
        3,
        "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FkL6aZ%2Fbtq93nFYWq1%2Ff6vKYZNPs0ZdKpQIZIDMsK%2Fimg.jpg",
        "what's your name?",
        FLAG_STATUS_PENDING,
        "12000",
        "#456456"
    ),
    AddFriends(
        1,
        "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FkL6aZ%2Fbtq93nFYWq1%2Ff6vKYZNPs0ZdKpQIZIDMsK%2Fimg.jpg",
        "Sim jh",
        FLAG_STATUS_PENDING,
        "3000",
        "#123123"
    ),
    AddFriends(
        2,
        "https://ibighit.com/bts/images/profile/proof/member/member-jimin.jpg",
        "Aki chu",
        FLAG_STATUS_ALREADY,
        "4000",
        "#321321"
    ), AddFriends(
        1,
        "https://ibighit.com/bts/images/profile/proof/member/member-jimin.jpg",
        "Sim jh",
        FLAG_STATUS_ADD,
        "3000",
        "#123123"
    ),
    AddFriends(
        2,
        "https://ibighit.com/bts/images/profile/proof/member/member-jimin.jpg",
        "Aki chu",
        FLAG_STATUS_ADD,
        "4000",
        "#321321"
    ),
    AddFriends(
        1,
        "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FkL6aZ%2Fbtq93nFYWq1%2Ff6vKYZNPs0ZdKpQIZIDMsK%2Fimg.jpg",
        "Sim jh",
        FLAG_STATUS_ADD,
        "3000",
        "#123123"
    ),
    AddFriends(
        2,
        "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FIuloj%2Fbtq98uDPwBx%2F1ManmKCAylybcg5Q3zno40%2Fimg.jpg",
        "Aki chu",
        FLAG_STATUS_ADD,
        "4000",
        "#321321"
    ),
    AddFriends(
        3,
        "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FkL6aZ%2Fbtq93nFYWq1%2Ff6vKYZNPs0ZdKpQIZIDMsK%2Fimg.jpg",
        "what's your name?",
        FLAG_STATUS_PENDING,
        "12000",
        "#456456"
    ),
    AddFriends(
        1,
        "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FkL6aZ%2Fbtq93nFYWq1%2Ff6vKYZNPs0ZdKpQIZIDMsK%2Fimg.jpg",
        "Sim jh",
        FLAG_STATUS_PENDING,
        "3000",
        "#123123"
    ),
    AddFriends(
        2,
        "https://ibighit.com/bts/images/profile/proof/member/member-jimin.jpg",
        "Aki chu",
        FLAG_STATUS_ALREADY,
        "4000",
        "#321321"
    ),
    AddFriends(
        1,
        "https://ibighit.com/bts/images/profile/proof/member/member-jimin.jpg",
        "Sim jh",
        FLAG_STATUS_ADD,
        "3000",
        "#123123"
    ),
    AddFriends(
        2,
        "https://ibighit.com/bts/images/profile/proof/member/member-jimin.jpg",
        "Aki chu",
        FLAG_STATUS_ADD,
        "4000",
        "#321321"
    )
)