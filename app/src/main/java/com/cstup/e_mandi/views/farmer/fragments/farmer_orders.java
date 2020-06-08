package com.cstup.e_mandi.views.farmer.fragments;

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
import com.cstup.e_mandi.controllers.orderController;
import com.cstup.e_mandi.model.Get.Order;
import com.cstup.e_mandi.model.Post.OrderId;
import com.cstup.e_mandi.utilities.constants;
import com.cstup.e_mandi.views.farmer.activites.HomeActivity;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class farmer_orders extends Fragment implements orderController.EventListener ,
        orderAdapter.EventListener , HomeActivity.OrderEventListener {

    private View emptyPage;
    private View errorPage;
    private RelativeLayout orderContainer;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ProgressBar progressBar;
    private TextView tryAgain;
    private orderController controller;

    private View orderDetails;
    private View loadingPanel;
    private LinearLayout contentContainer;
    private CircleImageView cropImage;
    private TextView cropName;
    private TextView orderQty;
    private TextView orderCost;
    private LinearLayout orderStatusContainer;
    private TextView orderConfirmedLine;
    private TextView orderConfirmedCircle;
    private TextView orderDeliveredLine;
    private TextView orderDeliveredCircle;
    private TextView deliveryAddress;
    private TextView cancelledOrderTv;
    private LinearLayout btnContainer;
    private TextView cancelOrder;
    private TextView orderStatus;
    private ProgressBar btnProgressBar;

    private TextView titleTv;
    private CircleImageView profileImage;
    private TextView contact;
    private TextView name;
    private TextView address;

    private SwipeRefreshLayout swipeRefreshLayout;
    public farmer_orders() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_farmer_orders, container, false);
        initViews(view);
        setRecyclerView();

        if(OrdersCache.isVendorOrderFirstCall){
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

        orderStatus.setOnClickListener(v -> {

        });

        cancelOrder.setOnClickListener(v -> {
            OrderId orderId = new OrderId();
            orderId.setOrder_id(OrdersCache.selectedOrder.getOrderId());
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
        loadingPanel = v.findViewById(R.id.loading_panel);
        contentContainer = v.findViewById(R.id.content_container);
        cropImage = v.findViewById(R.id.crop_image);
        cropName = v.findViewById(R.id.crop_name);
        orderQty = v.findViewById(R.id.order_qty);
        orderCost = v.findViewById(R.id.order_cost);
        orderStatusContainer = v.findViewById(R.id.status_container);
        orderConfirmedLine = v.findViewById(R.id.order_confirmed_line);
        orderConfirmedCircle = v.findViewById(R.id.order_confirmed_circle);
        orderDeliveredLine = v.findViewById(R.id.order_delivered_line);
        orderDeliveredCircle = v.findViewById(R.id.order_delivered_circle);
        deliveryAddress = v.findViewById(R.id.delivery_address);
        cancelledOrderTv = v.findViewById(R.id.cancelled_order_tv);
        btnContainer = v.findViewById(R.id.btn_container);
        cancelOrder = v.findViewById(R.id.cancel_order);
        orderStatus = v.findViewById(R.id.order_status);
        btnProgressBar = v.findViewById(R.id.btn_progressBar);

        titleTv = v.findViewById(R.id.title_tv);
        profileImage = v.findViewById(R.id.profile_image);
        contact = v.findViewById(R.id.contact);
        name = v.findViewById(R.id.name);
        address = v.findViewById(R.id.address);

        swipeRefreshLayout = v.findViewById(R.id.swipe_refresh);

        controller = new orderController(this);
        HomeActivity.orderEventListener = this;
    }

    private void setRecyclerView(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new orderAdapter(OrdersCache.vendorOrders , this);
        recyclerView.setAdapter(adapter);
    }

    private void hideErrorPage(){
        errorPage.setVisibility(View.GONE);
    }

    private void hideLoadingPanel(){loadingPanel.setVisibility(View.GONE);}

    private void setCropImage(String url){
        if(url != null)
            Glide.with(Objects.requireNonNull(getContext())).load(Uri.parse(url)).centerCrop().into(cropImage);
    }

    private void setCropName(String name){
        if(name != null)
            cropName.setText(name);
    }

    private void setOrderQty(double qty){
        String type = constants.TYPE_KG;
        if(qty > 100){
            qty /= 100;
            type = constants.TYPE_QUINTOL;
        }
        String qty_txt = getString(R.string.order_qty) + String.format("%.2f" , qty) + type;
        orderQty.setText(qty_txt);
    }

    private void setOrderCost(double cost){
        String order_cost_txt = Objects.requireNonNull(getContext()).getString(R.string.order_cost)
                + getString(R.string.rupee)
                + cost;
        orderCost.setText(order_cost_txt);
    }

    private void setOrderStatus(String status){
        switch (status){
            case OrdersCache.STATUS_CONFIRMED:
                fillOrderedConfirmedLine();
                fillOrderedConfirmedCircle();
                hideCancelledOrderTv();
                showOrderStatusContainer();
                break;

            case OrdersCache.STATUS_DELIVERED:
                fillOrderedDeliveredLine();
                fillOrderedDeliveredCircle();
                hideCancelledOrderTv();
                showOrderStatusContainer();
                break;
            case OrdersCache.STATUS_CANCELLED:
                hideOrderStatusContainer();
                showCancelledOrderTv();
                break;
        }
    }

    private void setDeliveryAddress(String address){
        if(address != null)
            deliveryAddress.setText("Delivery Address : " + address);
    }

    private void setTitleTv(){
        titleTv.setText(getString(R.string.customer));
    }

    private void showContentContainer(){
        contentContainer.setVisibility(View.VISIBLE);
    }

    private void fillOrderedDeliveredLine(){
        orderDeliveredLine.setBackgroundColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.green));
    }

    private void fillOrderedDeliveredCircle(){
        orderDeliveredCircle.setBackgroundColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.green));
    }

    private void fillOrderedConfirmedLine(){
        orderConfirmedLine.setBackgroundColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.green));
    }

    private void fillOrderedConfirmedCircle(){
        orderConfirmedCircle.setBackgroundColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.green));
    }

    private void showCancelOrderBtn(){cancelOrder.setVisibility(View.VISIBLE);}

    private void showOrderStatusBtn(){orderStatus.setVisibility(View.VISIBLE);}

    private void showCancelledOrderTv(){cancelledOrderTv.setVisibility(View.VISIBLE);}

    private void hideCancelledOrderTv(){cancelledOrderTv.setVisibility(View.VISIBLE);}

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
    public void showOrderDetails(Order order) {
        OrdersCache.isOrderDetailsVisible = true;
        controller.getDetails(order.getUserId());
        hideOrderContainer();
        hideLoadingPanel();
        showContentContainer();
        showCancelOrderBtn();
        showOrderStatusBtn();
        orderDetails.setVisibility(View.VISIBLE);
        setTitleTv();
        String url = order.getCropImage();
        if(url == null)
            url = order.getCropTypeImage();
        String name = order.getCropName();
        double qty = order.getItemQty();
        double cost = order.getItemFreezedCost();
        String status = order.getOrderStatus();
        String address = order.getDeliveryAddress();

        setCropImage(url);
        setCropName(name);
        setOrderQty(qty);
        setOrderCost(cost);
        setOrderStatus(status);
        setDeliveryAddress(address);
    }

    @Override
    public void hideOrderDetails() {
        orderDetails.setVisibility(View.GONE);
        OrdersCache.isOrderDetailsVisible = false;
    }
}
