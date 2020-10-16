package ist.uz.istchef.screen.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ist.uz.istchef.model.*
import ist.uz.istchef.repository.UserRepository

class MainViewModel() : ViewModel() {

    val userRepository = UserRepository()

    val error = MutableLiveData<String>()
    val progress = MutableLiveData<Boolean>()
    val orderFoodsSending = MutableLiveData<List<OrderFoodModel>>()
    val orderFoodsCompleted = MutableLiveData<List<OrderFoodModel>>()
    val orderFoodsProcessing = MutableLiveData<List<OrderFoodModel>>()
    val orderFoodProcessData = MutableLiveData<Boolean>()
    val userData = MutableLiveData<UserModel>()
    val getOrders = MutableLiveData<List<ProductModel>>()
    val foodCancel=MutableLiveData<Boolean>()
    val foodHave=MutableLiveData<Boolean>()

    fun getSendingOrders() {
        userRepository.getOrders("sending", progress, error, orderFoodsSending)
    }

    fun getProcessingOrders() {
        userRepository.getOrders("processing", progress, error, orderFoodsProcessing)
    }

    fun getCompletedOrders() {
        userRepository.getOrders("completed", progress, error, orderFoodsCompleted)
    }

    fun orderFoodProcess(id: Int) {
        userRepository.orderFoodProcess(id, progress, error, orderFoodProcessData)
    }

    fun orderFoodComplete(id: Int) {
        userRepository.orderFoodComplete(id, progress, error, orderFoodProcessData)
    }

    fun getUser() {
        userRepository.getUser(progress, error, userData)
    }

    fun getMyOrders() {
        userRepository.getMyFoods(progress, error, getOrders)
    }

    fun orderFoodCancel(order_food_id: Int, comment: String) {
        userRepository.orderFoodCancle(order_food_id,comment,progress,error,foodCancel)
    }
    fun orderFoodHave(food_id: Int, is_have: Boolean) {
        userRepository.orderFoodHave(food_id,is_have,progress,error,foodHave)
    }
}