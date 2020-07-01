package com.cstup.e_mandi.views.user.fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cstup.e_mandi.Cache.OrdersCache;
import com.cstup.e_mandi.R;
import com.cstup.e_mandi.adapters.orderAdapter;
import com.cstup.e_mandi.adapters.orderItemsAdapter;
import com.cstup.e_mandi.controllers.orderController;
import com.cstup.e_mandi.model.Get.PartialOrder;
import com.cstup.e_mandi.model.OrderDetails;
import com.cstup.e_mandi.model.Post.OrderId;
import com.cstup.e_mandi.views.user.activites.BottomNavigation;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class orders extends Fragment implements orderController.EventListener ,
        orderAdapter.EventListener , BottomNavigation.orderEventListener {
    private View emptyPage;
    private View errorPage;
    private RelativeLayout orderContainer;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ProgressBar progressBar;
    private TextView tryAgain;
    private orderController controller;

    private View orderDetails;
    private LinearLayout orderStatusContainer;
    private TextView orderConfirmedLine;
    private TextView orderConfirmedCircle;
    private TextView orderDeliveredLine;
    private TextView orderDeliveredCircle;
    private TextView cancelledOrderTv;
    private TextView deliveryAddress;

    private LinearLayout btnContainer;
    private TextView cancelOrder;
    private ProgressBar btnProgressBar;

    private TextView titleTv;
    private CircleImageView profileImage;
    private TextView contact;
    private TextView name;
    private TextView address;

    private SwipeRefreshLayout swipeRefreshLayout;

    private RecyclerView orderDetailsRV;
    private RecyclerView.Adapter orderDetailsAdapter;
    public orders() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        initViews(view);
        setRecyclerView();

        if(OrdersCache.isUserOrderFirstCall){
            showProgressBar();
            controller.fetchOrders();
        }

        tryAgain.setOnClickListener(v -> {
            showProgressBar();
            hideErrorPage();
            showOrderContainer();
            controller.fetchOrders();
        });

        swipeRefreshLayout.setOnRefreshListener(() -> {
            hideEmptyPage();
            showProgressBar();
            controller.fetchOrders();
            swipeRefreshLayout.setRefreshing(false);
        });

        cancelOrder.setOnClickListener(v -> {
            OrderId orderId = new OrderId();
            orderId.setOrder_id(OrdersCache.selectedOrderId);
            controller.cancelOrder(orderId);
        });

        return view;
    }

    private void initViews(View v){
        emptyPage = v.findViewById(R.id.empty_page);
        errorPage = v.findViewById(R.id.error_view);
        orderContainer = v.findViewById(R.id.order_container);
        recyclerView = v.findViewById(R.id.recyclerView);
        progressBar = v.findViewById(R.id.progressBar);
        tryAgain = v.findViewById(R.id.try_again_tv);

        orderDetails = v.findViewById(R.id.order_details);
        orderStatusContainer = v.findViewById(R.id.status_container);
        orderConfirmedLine = v.findViewById(R.id.order_confirmed_line);
        orderConfirmedCircle = v.findViewById(R.id.order_confirmed_circle);
        orderDeliveredLine = v.findViewById(R.id.order_delivered_line);
        orderDeliveredCircle = v.findViewById(R.id.order_delivered_circle);
        deliveryAddress = v.findViewById(R.id.delivery_address);
        cancelledOrderTv = v.findViewById(R.id.cancelled_order_tv);
        btnContainer = v.findViewById(R.id.btn_container);
        cancelOrder = v.findViewById(R.id.cancel_order);
        btnProgressBar = v.findViewById(R.id.btn_progressBar);

        titleTv = v.findViewById(R.id.title_tv);
        profileImage = v.findViewById(R.id.profile_image);
        contact = v.findViewById(R.id.contact);
        name = v.findViewById(R.id.name);
        address = v.findViewById(R.id.address);

        swipeRefreshLayout = v.findViewById(R.id.swipe_refresh);

        orderDetailsRV = v.findViewById(R.id.order_items_rv);

        controller = new orderController(this);
        BottomNavigation.orderEventListener = this;
    }

    private void setRecyclerView(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new orderAdapter(OrdersCache.userPartialOrders , this);
        recyclerView.setAdapter(adapter);
    }

    private void hideErrorPage(){
        errorPage.setVisibility(View.GONE);
    }


    private void setOrderStatus(String status){
        String GREEN = "GREEN";
        String GRAY = "GRAY";
        switch (status){
            case OrdersCache.STATUS_CONFIRMED:
                fillOrderedConfirmedLine(GREEN);
                fillOrderedConfirmedCircle(GREEN);
                fillOrderedDeliveredCircle(GRAY);
                fillOrderedDeliveredLine(GRAY);
                hideCancelledOrderTv();
                showOrderStatusContainer();
                showBtnContainer();
                break;

            case OrdersCache.STATUS_DELIVERED:
                fillOrderedConfirmedLine(GREEN);
                fillOrderedConfirmedCircle(GREEN);
                fillOrderedDeliveredLine(GREEN);
                fillOrderedDeliveredCircle(GREEN);
                hideCancelledOrderTv();
                showOrderStatusContainer();
                hideBtnContainer();
                break;
            case OrdersCache.STATUS_CANCELLED:
                hideOrderStatusContainer();
                showCancelledOrderTv();
                hideBtnContainer();
                break;
            default:
                fillOrderedConfirmedLine(GRAY);
                fillOrderedConfirmedCircle(GRAY);
                fillOrderedDeliveredLine(GRAY);
                fillOrderedDeliveredCircle(GRAY);
                showOrderStatusContainer();
                hideCancelledOrderTv();
                showBtnContainer();
                break;
        }
    }

    private void setDeliveryAddress(String address){
        if(address != null)
            deliveryAddress.setText("Delivery Address : " + address);
    }

    private void setTitleTv(){
        titleTv.setText(getString(R.string.vendor));
    }

    private void showOrderDetails(){
        orderDetails.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setEnabled(false);
    }

    private void fillOrderedDeliveredLine(String color){
        switch (color){
            case "GREEN":
                orderDeliveredLine.setBackgroundColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.green));
                break;
            case "GRAY":
                orderDeliveredLine.setBackgroundColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.light_gray));
        }
    }

    private void fillOrderedDeliveredCircle(String color){
        switch (color){
            case "GREEN":
                orderDeliveredCircle.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.cust_circle_green_back));
                break;
            case "GRAY":
                orderDeliveredCircle.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.cust_gray_circle));
                break;
        }
    }

    private void fillOrderedConfirmedLine(String color){
        switch (color){
            case "GREEN":
                orderConfirmedLine.setBackgroundColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.green));
                break;
            case "GRAY":
                orderConfirmedLine.setBackgroundColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.light_gray));
        }
    }

    private void fillOrderedConfirmedCircle(String color){
        switch (color){
            case "GREEN":
                orderConfirmedCircle.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.cust_circle_green_back));
                break;
            case "GRAY":
                orderConfirmedCircle.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.cust_gray_circle));
                break;
        }

    }

    private void showCancelOrderBtn(){cancelOrder.setVisibility(View.VISIBLE);}

    private void showCancelledOrderTv(){cancelledOrderTv.setVisibility(View.VISIBLE);}

    private void hideCancelledOrderTv(){cancelledOrderTv.setVisibility(View.GONE);}

    private void showOrderStatusContainer(){orderStatusContainer.setVisibility(View.VISIBLE);}

    private void hideOrderStatusContainer(){orderStatusContainer.setVisibility(View.GONE);}

    @Override
    public void setProfileImage(String url){
        if(url != null)
            Glide.with(Objects.requireNonNull(getContext())).load(Uri.parse(url)).centerCrop().into(profileImage);
    }

    @Override
    public void setName(String s){
        if(s != null)
            name.setText(s);
    }

    @Override
    public void setAddress(String add){
        if(add != null)
            address.setText(add);
    }

    @Override
    public void setContact(String num){
        if(num != null)
            contact.setText(num);
    }

    @Override
    public void showBtnProgressBar() {
        btnProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideBtnProgressBar() {
        btnProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showBtnContainer() {
        btnContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideBtnContainer() {
        btnContainer.setVisibility(View.GONE);
    }

    @Override
    public void showProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar(){
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showEmptyPage(){
        emptyPage.setVisibility(View.VISIBLE);
    }

    private void hideEmptyPage(){emptyPage.setVisibility(View.GONE);}

    @Override
    public void showErrorPage(){
        errorPage.setVisibility(View.VISIBLE);
    }

    @Override
    public void showOrderContainer(){
        orderContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void notifyOrderDetails() {
        orderDetailsAdapter.notifyDataSetChanged();
    }

    @Override
    public void displayOrderDetails(OrderDetails orderDetails) {
        setDeliveryAddress(orderDetails.getDeliveryAddress());
        setOrderStatus(orderDetails.getOrderStatus());
    }

    @Override
    public void hideOrderContainer(){
        orderContainer.setVisibility(View.GONE);
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getContext() , msg , Toast.LENGTH_LONG).show();
    }

    @Override
    public void notifyChanges(){
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showOrderDetails(PartialOrder partialOrder) {
        OrdersCache.isOrderDetailsVisible = true;
        controller.getDetails(partialOrder.getVendorId());

        hideOrderContainer();
        showOrderDetails();
        showCancelOrderBtn();
        orderDetails.setVisibility(View.VISIBLE);
        setTitleTv();
        setOrderDetailsRV();
        controller.fetchOrderItem(partialOrder.getOrderId());
    }

    private void setOrderDetailsRV(){
        orderDetailsRV.setHasFixedSize(true);
        orderDetailsRV.setLayoutManager(new LinearLayoutManager(getContext()));
        orderDetailsAdapter = new orderItemsAdapter(OrdersCache.userOrders);
        orderDetailsRV.setAdapter(orderDetailsAdapter);
        notifyOrderDetails();
    }

    @Override
    public void hideOrderDetails() {
        orderDetails.setVisibility(View.GONE);
        OrdersCache.isOrderDetailsVisible = false;
        swipeRefreshLayout.setEnabled(true);
    }
}
