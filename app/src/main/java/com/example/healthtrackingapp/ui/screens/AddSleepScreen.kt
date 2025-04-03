package com.example.healthtrackingapp.ui.screens

import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.example.healthtrackingapp.R
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import showNotification

@Composable
fun AddSleepScreen(
    navController: NavHostController
) {
    val db = FirebaseFirestore.getInstance()
    var user by remember { mutableStateOf(Firebase.auth.currentUser) }

    Scaffold { innerPadding ->

    AndroidView(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        factory = { context ->
            val view = LayoutInflater.from(context).inflate(R.layout.nhap_giac_ngu, null, false)

            val btnClose = view.findViewById<ImageView>(R.id.imgDong)
            btnClose.setOnClickListener {
                navController.popBackStack()
            }

            val edtGiacngu = view.findViewById<EditText>(R.id.edtGiacNgu)

            val radioGroupSleepQuality = view.findViewById<RadioGroup>(R.id.radioGroupSleepQuality)
            val radioGroupMorningFeel = view.findViewById<RadioGroup>(R.id.radioGroupMorningFeel)
            val radioGroupSleepEase = view.findViewById<RadioGroup>(R.id.radioGroupSleepEase)

            val btnSave = view.findViewById<Button>(R.id.btnXacNhanOTP)
            btnSave.setOnClickListener {
                // Kiểm tra và lấy dữ liệu
                try {
                    val edtGiacnguText = edtGiacngu.text.toString()
                    if (edtGiacnguText.isEmpty()) {
                        Toast.makeText(context, "Vui lòng nhập số giờ ngủ", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }

                    val sleep = edtGiacnguText.toDouble()
                    val sleepQuality = findCheckedIndex(radioGroupSleepQuality)
                    val morningFeel = findCheckedIndex(radioGroupMorningFeel)
                    val sleepEase = findCheckedIndex(radioGroupSleepEase)

                    // Tính điểm và đánh giá
                    val score = sleepQuality + morningFeel + sleepEase
                    val message = when {
                        score >= 8 -> "Chất lượng giấc ngủ rất tốt!"
                        score == 6 || score == 7 -> "Chất lượng giấc ngủ khá ổn."
                        score == 4 || score == 5 -> "Chất lượng giấc ngủ trung bình"
                        score == 2 || score == 3 -> "Chất lượng giấc ngủ kém"
                        else -> "Chất lượng giấc ngủ rất kém"
                    }

                    // Lưu dữ liệu
                    db.collection("users")
                        .document(user!!.uid)
                        .collection("sleep")
                        .add(
                            hashMapOf(
                                "giacngu" to edtGiacnguText,
                                "quality" to message,
                                "timestamp" to System.currentTimeMillis()
                            )
                        )

                    // Phân tích và gửi thông báo
                    when {
                        (sleep >= 8.0) -> {
                            // Thông báo ngủ đủ giấc
                            db.collection("users")
                                .document(user!!.uid)
                                .collection("notification")
                                .add(
                                    hashMapOf(
                                        "title" to "Giờ ngủ",
                                        "description" to "Giờ ngủ bạn là "+sleep+" giờ, bạn đã ngủ đủ giấc.",
                                        "icon" to "Sleep",
                                        "color" to "0xFFBD2E4B",
                                        "timestamp" to Timestamp.now(),
                                        "unread" to true
                                    )
                                )
                            context.showNotification(
                                channelId = "sleep_chanel",
                                notificationId = 3,
                                title = "Giờ ngủ",
                                content = "Giờ ngủ bạn là "+sleep+" giờ, bạn đã ngủ đủ giấc."
                            )
                        }
                        (sleep >= 6.0 && sleep < 8.0) -> {
                            // Thêm thông báo cho khoảng này
                            db.collection("users")
                                .document(user!!.uid)
                                .collection("notification")
                                .add(
                                    hashMapOf(
                                        "title" to "Giờ ngủ",
                                        "description" to "Giờ ngủ bạn là "+sleep+" giờ. Bạn đang ngủ ở mức đủ, nhưng có thể cải thiện thêm.",
                                        "icon" to "Sleep",
                                        "color" to "0xFFBD2E4B",
                                        "timestamp" to Timestamp.now(),
                                        "unread" to true
                                    )
                                )
                            context.showNotification(
                                channelId = "sleep_chanel",
                                notificationId = 3,
                                title = "Giờ ngủ",
                                content = "Giờ ngủ bạn là "+sleep+" giờ. Bạn đang ngủ ở mức đủ, nhưng có thể cải thiện thêm."
                            )
                        }
                        (sleep <= 6.0 && sleep >= 3.0) -> {
                            // Sửa lại điều kiện để bao gồm cả 3.0 và 6.0
                            db.collection("users")
                                .document(user!!.uid)
                                .collection("notification")
                                .add(
                                    hashMapOf(
                                        "title" to "Giờ ngủ",
                                        "description" to "Giờ ngủ bạn là "+sleep+" giờ. Bạn bị thiếu ngủ, cần chú ý.",
                                        "icon" to "Sleep",
                                        "color" to "0xFFBD2E4B",
                                        "timestamp" to Timestamp.now(),
                                        "unread" to true
                                    )
                                )
                            context.showNotification(
                                channelId = "sleep_chanel",
                                notificationId = 3,
                                title = "Giờ ngủ",
                                content = "Giờ ngủ bạn là "+sleep+" giờ. Bạn bị thiếu ngủ, cần chú ý."
                            )
                        }
                        else -> {
                            // Trường hợp còn lại (sleep < 3.0)
                            db.collection("users")
                                .document(user!!.uid)
                                .collection("notification")
                                .add(
                                    hashMapOf(
                                        "title" to "Giờ ngủ",
                                        "description" to "Giờ ngủ bạn là "+sleep+" giờ. Bạn đang ngủ quá ít, có thể ảnh hưởng tới sức khỏe.",
                                        "icon" to "Sleep",
                                        "color" to "0xFFBD2E4B",
                                        "timestamp" to Timestamp.now(),
                                        "unread" to true
                                    )
                                )
                            context.showNotification(
                                channelId = "sleep_chanel",
                                notificationId = 3,
                                title = "Giờ ngủ",
                                content = "Giờ ngủ bạn là "+sleep+" giờ. Bạn đang ngủ quá ít, có thể ảnh hưởng tới sức khỏe."
                            )
                        }
                    }

                    navController.popBackStack()
                } catch (e: NumberFormatException) {
                    Toast.makeText(context, "Vui lòng nhập số giờ ngủ hợp lệ", Toast.LENGTH_SHORT).show()
                }
            }
            view
        }
    )
    }
}

fun findCheckedIndex(radioGroup: RadioGroup): Int {
    return when (radioGroup.checkedRadioButtonId) {
        R.id.mot_a, R.id.hai_a, R.id.ba_a -> 3
        R.id.mot_b, R.id.hai_b, R.id.ba_b -> 2
        R.id.mot_c, R.id.hai_c, R.id.ba_c -> 1
        R.id.mot_d, R.id.hai_d, R.id.ba_d -> 0
        else -> 0
    }
}
