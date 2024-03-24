package com.example.vipassignment

class User(val user_id: String, val username: String, val phone: String, val city: String, val country: String, val profilepic: String, val coverpic: String) {
    constructor() : this("", "", "", "", "", "", "")
}