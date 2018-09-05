package com.layanacomputindo.bprcma.form

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class Kesimpulan {
    @SerializedName("id")
    @Expose
    private var id: Int? = null
    @SerializedName("karakter")
    @Expose
    private var karakter: String? = null
    @SerializedName("kapasitas")
    @Expose
    private var kapasitas: String? = null
    @SerializedName("kondisi")
    @Expose
    private var kondisi: String? = null
    @SerializedName("kapital")
    @Expose
    private var kapital: String? = null
    @SerializedName("jaminan")
    @Expose
    private var jaminan: String? = null
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

    fun getKarakter(): String? {
        return karakter
    }

    fun setKarakter(karakter: String) {
        this.karakter = karakter
    }

    fun getKapasitas(): String? {
        return kapasitas
    }

    fun setKapasitas(kapasitas: String) {
        this.kapasitas = kapasitas
    }

    fun getKondisi(): String? {
        return kondisi
    }

    fun setKondisi(kondisi: String) {
        this.kondisi = kondisi
    }

    fun getKapital(): String? {
        return kapital
    }

    fun setKapital(kapital: String) {
        this.kapital = kapital
    }

    fun getJaminan(): String? {
        return jaminan
    }

    fun setJaminan(jaminan: String) {
        this.jaminan = jaminan
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