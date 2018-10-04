package com.layanacomputindo.bprcma.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class HargaBangunan {
    @SerializedName("id")
    @Expose
    private var id: Int? = null
    @SerializedName("faktor_penilaian_id")
    @Expose
    private var faktorPenilaianId: Int? = null
    @SerializedName("pengembang")
    @Expose
    private var pengembang: Int? = null
    @SerializedName("taksiran_penilai")
    @Expose
    private var taksiranPenilai: Int? = null
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

    fun getFaktorPenilaianId(): Int? {
        return faktorPenilaianId
    }

    fun setFaktorPenilaianId(faktorPenilaianId: Int?) {
        this.faktorPenilaianId = faktorPenilaianId
    }

    fun getPengembang(): Int? {
        return pengembang
    }

    fun setPengembang(pengembang: Int?) {
        this.pengembang = pengembang
    }

    fun getTaksiranPenilai(): Int? {
        return taksiranPenilai
    }

    fun setTaksiranPenilai(taksiranPenilai: Int?) {
        this.taksiranPenilai = taksiranPenilai
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