package com.example.cannintickets.repositories;

import androidx.annotation.NonNull;

import com.example.cannintickets.entities.order.OrderEntity;
import com.example.cannintickets.models.orders.OrderPersistenceModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;


public class OrdersRepotitory implements OrderEntity {
   final FirebaseFirestore db;

   public OrdersRepotitory() {
       this.db = FirebaseFirestore.getInstance();
   }



    public CompletableFuture<List<OrderPersistenceModel>> getFromEvent(String eventId) {
        CompletableFuture<List<OrderPersistenceModel>> future = new CompletableFuture<>();
        db.collection("Order")
                .whereEqualTo("eventId", eventId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<OrderPersistenceModel> looplist = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()){
                                OrderPersistenceModel order = document.toObject(OrderPersistenceModel.class);
                                order.setId(document.getId());
                                looplist.add(order);
                            }
                            future.complete(looplist);
                        } else {
                            future.complete(null);
                        }
                    }
                });

        return future;
    }


    public CompletableFuture<List<OrderPersistenceModel>> getFromCustomer(String customerEmail) {
       CompletableFuture<List<OrderPersistenceModel>> future = new CompletableFuture<>();
       db.collection("Order")
               .whereEqualTo("customerEmail", customerEmail)
               .get()
               .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<OrderPersistenceModel> looplist = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()){
                                OrderPersistenceModel order = document.toObject(OrderPersistenceModel.class);
                                order.setId(document.getId());
                                looplist.add(order);
                            }
                            future.complete(looplist);
                        } else {
                            future.complete(null);
                        }
                   }
               });

       return future;
   }


   public CompletableFuture<String> create(OrderPersistenceModel order) {
       CompletableFuture<String> future = new CompletableFuture<>();
       Map<String, Object> newOrder = new HashMap<>();
       newOrder.put("createdAt", order.getCreatedAt());
       newOrder.put("customerEmail", order.getCustomerEmail());
       newOrder.put("eventId", order.getEventId());
       newOrder.put("eventName", order.getEventName());
       newOrder.put("paymentIntentId", order.getPaymentIntentId());
       newOrder.put("quantity", order.getQuantity());
       newOrder.put("ticketId", order.getTicketId());
       newOrder.put("ticketName", order.getTicketName());
       newOrder.put("ticketPrice", order.getTicketPrice());
       newOrder.put("total", order.getTotal());
       newOrder.put("updatedAt", order.getUpdatedAt());

       db.collection("Order")
               .add(newOrder)
               .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                   @Override
                   public void onSuccess(DocumentReference documentReference) {
                       future.complete("The order was created successfully");
                   }
               })
               .addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                       future.complete(e.toString());
                   }
               });

       return future;

   }
}
