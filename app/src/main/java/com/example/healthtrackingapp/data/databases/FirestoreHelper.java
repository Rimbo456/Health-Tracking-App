package com.example.healthtrackingapp.data.databases;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.Map;

public class FirestoreHelper {
    private static FirestoreHelper instance;
    private final FirebaseFirestore db;

    public FirestoreHelper() {
        db = FirebaseFirestore.getInstance();
    }

    public void getUserData(String userId, OnSuccessListener<DocumentSnapshot> listener, OnFailureListener failureListener) {
        db.collection("users").document(userId)
                .get()
                .addOnSuccessListener(listener)
                .addOnFailureListener(failureListener);
    }

    public void updateUserName(String userId, String newName, OnSuccessListener<Void> listener, OnFailureListener failureListener) {
        db.collection("users").document(userId)
                .update("username", newName)
                .addOnSuccessListener(listener)
                .addOnFailureListener(failureListener);
    }

    public static FirestoreHelper getInstance() {
        if (instance == null) {
            instance = new FirestoreHelper();
        }
        return instance;
    }

    // Thêm dữ liệu vào Firestore
    public void addDocument(String collection, String documentId, Map<String, Object> data, FirestoreCallback callback) {
        db.collection(collection).document(documentId)
                .set(data)
                .addOnSuccessListener(unused -> callback.onSuccess())
                .addOnFailureListener(callback::onFailure);
    }

    // Đọc dữ liệu từ Firestore
    public void getDocument(String collection, String documentId, FirestoreDataCallback callback) {
        db.collection(collection).document(documentId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        callback.onSuccess(documentSnapshot.getData());
                    } else {
                        callback.onFailure(new Exception("Document không tồn tại"));
                    }
                })
                .addOnFailureListener(callback::onFailure);
    }

    // Cập nhật dữ liệu
    public void updateDocument(String collection, String documentId, Map<String, Object> updates, FirestoreCallback callback) {
        db.collection(collection).document(documentId)
                .update(updates)
                .addOnSuccessListener(unused -> callback.onSuccess())
                .addOnFailureListener(callback::onFailure);
    }

    // Xóa tài liệu
    public void deleteDocument(String collection, String documentId, FirestoreCallback callback) {
        db.collection(collection).document(documentId)
                .delete()
                .addOnSuccessListener(unused -> callback.onSuccess())
                .addOnFailureListener(callback::onFailure);
    }

    // Interface Callback
    public interface FirestoreCallback {
        void onSuccess();
        void onFailure(Exception e);
    }

    public interface FirestoreDataCallback {
        void onSuccess(Map<String, Object> data);
        void onFailure(Exception e);
    }
}

