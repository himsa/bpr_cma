package com.layanacomputindo.bprcma.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class User {
    @SerializedName("id")
    @Expose
    private var id: Int? = null
    @SerializedName("name")
    @Expose
    private var name: String? = null
    @SerializedName("email")
    @Expose
    private var email: String? = null
    @SerializedName("role")
    @Expose
    private var role: String? = null
    @SerializedName("alamat")
    @Expose
    private var alamat: Any? = null
    @SerializedName("foto")
    @Expose
    private var foto: Any? = null
    @SerializedName("created_at")
    @Expose
    private var createdAt: String? = null
    @SerializedName("updated_at")
    @Expose
    private var updatedAt: String? = null

    fun getId(): Int? {
        return id
    }

    fun setId(id: Int?) {
        this.id = id
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getEmail(): String? {
        return email
    }

    fun setEmail(email: String) {
        this.email = email
    }

    fun getRole(): String? {
        return role
    }

    fun setRole(role: String) {
        this.role = role
    }

    fun getAlamat(): Any? {
        return alamat
    }

    fun setAlamat(alamat: Any) {
        this.alamat = alamat
    }

    fun getFoto(): Any? {
        return foto
    }

    fun setFoto(foto: Any) {
        this.foto = foto
    }

    fun getCreatedAt(): String? {
        return createdAt
    }

    fun setCreatedAt(createdAt: String) {
        this.createdAt = createdAt
    }

    fun getUpdatedAt(): String? {
        return updatedAt
    }

    fun setUpdatedAt(updatedAt: String) {
        this.updatedAt = updatedAt
    }
}