package com.layanacomputindo.bprcma.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class FotoPasangan {
    @SerializedName("id")
    @Expose
    private var id: Int? = null
    @SerializedName("pasangan_debitur_id")
    @Expose
    private var pasanganDebiturId: Int? = null
    @SerializedName("foto")
    @Expose
    private var foto: String? = null
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

    fun getPasanganDebiturId(): Int? {
        return pasanganDebiturId
    }

    fun setPasanganDebiturId(pasanganDebiturId: Int?) {
        this.pasanganDebiturId = pasanganDebiturId
    }

    fun getFoto(): String? {
        return foto
    }

    fun setFoto(foto: String) {
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