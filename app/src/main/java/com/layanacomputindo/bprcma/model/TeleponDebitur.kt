package com.layanacomputindo.bprcma.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class TeleponDebitur {
    @SerializedName("id")
    @Expose
    private var id: Int? = null
    @SerializedName("debitur_id")
    @Expose
    private var debiturId: Int? = null
    @SerializedName("no_telepon")
    @Expose
    private var noTelepon: String? = null
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

    fun getDebiturId(): Int? {
        return debiturId
    }

    fun setDebiturId(debiturId: Int?) {
        this.debiturId = debiturId
    }

    fun getNoTelepon(): String? {
        return noTelepon
    }

    fun setNoTelepon(noTelepon: String) {
        this.noTelepon = noTelepon
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