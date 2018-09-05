package com.layanacomputindo.bprcma.form

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class FotoDokumenLain {
    @SerializedName("id")
    @Expose
    private var id: Int? = null
    @SerializedName("foto")
    @Expose
    private var foto: String? = null
    @SerializedName("created_at")
    @Expose
    private var createdAt: String? = null
    @SerializedName("updated_at")
    @Expose
    private var updatedAt: String? = null
    @SerializedName("kredit_id")
    @Expose
    private var kreditId: Int? = null

    fun getId(): Int? {
        return id
    }

    fun setId(id: Int?) {
        this.id = id
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

    fun getKreditId(): Int? {
        return kreditId
    }

    fun setKreditId(kreditId: Int?) {
        this.kreditId = kreditId
    }
}