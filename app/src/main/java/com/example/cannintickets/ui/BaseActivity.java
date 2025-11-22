package com.example.cannintickets.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import androidx.core.content.ContextCompat;

import com.example.cannintickets.R;
import com.example.cannintickets.controllers.auth.GetUserController;

public abstract class BaseActivity extends AppCompatActivity {

    private ImageButton eventsBtn, ticketsBtn, profileBtn, orderBtn;
    private LinearLayout ordersLayout;

    private String role;

    private ColorStateList defaultTint;
    private ColorStateList selectedTint;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        eventsBtn = findViewById(R.id.events);
        ticketsBtn = findViewById(R.id.tickets);
        profileBtn = findViewById(R.id.profile);
        ordersLayout = findViewById(R.id.orders);
        orderBtn = findViewById(R.id.order_button_clickable);

        defaultTint  = ColorStateList.valueOf(
                ContextCompat.getColor(this, R.color.bluegray));
        selectedTint = ColorStateList.valueOf(
                ContextCompat.getColor(this, R.color.orange));

        eventsBtn.setImageTintList(defaultTint);
        ticketsBtn.setImageTintList(defaultTint);
        profileBtn.setImageTintList(defaultTint);
        if (orderBtn != null) {
            orderBtn.setImageTintList(defaultTint);
        }

        if (this instanceof BuyerEventsActivity || this instanceof SellerEventsActivity) {
            selectButton(eventsBtn);
        } else if (this instanceof UserTicketActivity || this instanceof SeeTicketsActivity) {
            selectButton(ticketsBtn);
        } else if (this instanceof SeeOrdersActivity) {
            selectButton(orderBtn);
        } else if (this instanceof SignUpActivity) {
            selectButton(profileBtn);
        }

        GetUserController endpoint = new GetUserController();
        endpoint.POST()
                .thenAccept(response -> {
                    role = response.getRole();

                    runOnUiThread(() -> {
                        if ("SELLER".equals(role)) {
                            if (ordersLayout != null) {
                                ordersLayout.setVisibility(View.VISIBLE);
                            }
                        } else {
                            if (ordersLayout != null) {
                                ordersLayout.setVisibility(View.GONE);
                            }
                        }

                        eventsBtn.setOnClickListener(v -> {
                            selectButton(eventsBtn);

                            if ("BUYER".equals(role)
                                    && !(BaseActivity.this instanceof BuyerEventsActivity)) {
                                startActivity(new Intent(BaseActivity.this, BuyerEventsActivity.class));
                            } else if ("SELLER".equals(role)
                                    && !(BaseActivity.this instanceof SellerEventsActivity)) {
                                startActivity(new Intent(BaseActivity.this, SellerEventsActivity.class));
                            }
                        });

                        ticketsBtn.setOnClickListener(v -> {
                            selectButton(ticketsBtn);

                            if ("BUYER".equals(role)
                                    && !(BaseActivity.this instanceof UserTicketActivity)) {
                                startActivity(new Intent(BaseActivity.this, UserTicketActivity.class));
                            } else if ("SELLER".equals(role)
                                    && !(BaseActivity.this instanceof SeeTicketsActivity)) {
                                startActivity(new Intent(BaseActivity.this, SeeTicketsActivity.class));
                            }
                        });

                        profileBtn.setOnClickListener(v -> {
                            selectButton(profileBtn);
                            if (!(BaseActivity.this instanceof SignUpActivity)) {
                                startActivity(new Intent(BaseActivity.this, SignUpActivity.class));
                            }
                        });

                        if (orderBtn != null) {
                            orderBtn.setOnClickListener(v -> {
                                selectButton(orderBtn);
                                if ("SELLER".equals(role)
                                        && !(BaseActivity.this instanceof SeeOrdersActivity)) {
                                    startActivity(new Intent(BaseActivity.this, SeeOrdersActivity.class));
                                }
                            });
                        }
                    });
                })
                .exceptionally(throwable -> {
                    runOnUiThread(() ->
                            Toast.makeText(BaseActivity.this, "Error getting user", Toast.LENGTH_SHORT).show()
                    );
                    return null;
                });
    }

    protected void setChildContentView(@LayoutRes int layoutResId) {
        FrameLayout contentFrame = findViewById(R.id.content_frame);
        LayoutInflater.from(this).inflate(layoutResId, contentFrame, true);
    }

    private void selectButton(ImageButton selectedButton) {
        if (eventsBtn != null) eventsBtn.setImageTintList(defaultTint);
        if (ticketsBtn != null) ticketsBtn.setImageTintList(defaultTint);
        if (profileBtn != null) profileBtn.setImageTintList(defaultTint);
        if (orderBtn != null) orderBtn.setImageTintList(defaultTint);

        if (selectedButton != null) {
            selectedButton.setImageTintList(selectedTint);
        }
    }
}
