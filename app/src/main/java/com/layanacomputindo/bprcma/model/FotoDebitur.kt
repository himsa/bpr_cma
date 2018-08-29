package com.layanacomputindo.bprcma.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class FotoDebitur {
    @SerializedName("id")
    @Expose
    private var id: Int? = null
    @SerializedName("debitur_id")
    @Expose
    private var debiturId: Int? = null
    @SerializedName("foto_ktp")
    @Expose
    private var fotoKtp: String? = null
    @SerializedName("foto_dengan_ktp")
    @Expose
    private var fotoDenganKtp: String? = null
    @SerializedName("foto_kartu_keluarga")
    @Expose
    private var fotoKartuKeluarga: String? = null
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

    fun getFotoKtp(): String? {
        return fotoKtp
    }

    fun setFotoKtp(fotoKtp: String) {
        this.fotoKtp = fotoKtp
    }

    fun getFotoDenganKtp(): String? {
        return fotoDenganKtp
    }

    fun setFotoDenganKtp(fotoDenganKtp: String) {
        this.fotoDenganKtp = fotoDenganKtp
    }

    fun getFotoKartuKeluarga(): String? {
        return fotoKartuKeluarga
    }

    fun setFotoKartuKeluarga(fotoKartuKeluarga: String) {
        this.fotoKartuKeluarga = fotoKartuKeluarga
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