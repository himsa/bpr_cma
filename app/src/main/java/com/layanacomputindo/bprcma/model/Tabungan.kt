package com.layanacomputindo.bprcma.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class Tabungan {
    @SerializedName("id")
    @Expose
    private var id: Int? = null
    @SerializedName("nomor_rekening")
    @Expose
    private var nomorRekening: String? = null
    @SerializedName("nomor_bilyet")
    @Expose
    private var nomorBilyet: String? = null
    @SerializedName("nama")
    @Expose
    private var nama: String? = null
    @SerializedName("nominal")
    @Expose
    private var nominal: Int? = null
    @SerializedName("foto_dokumen")
    @Expose
    private var fotoDokumen: String? = null
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

    fun getNomorRekening(): String? {
        return nomorRekening
    }

    fun setNomorRekening(nomorRekening: String) {
        this.nomorRekening = nomorRekening
    }

    fun getNomorBilyet(): String? {
        return nomorBilyet
    }

    fun setNomorBilyet(nomorBilyet: String) {
        this.nomorBilyet = nomorBilyet
    }

    fun getNama(): String? {
        return nama
    }

    fun setNama(nama: String) {
        this.nama = nama
    }

    fun getNominal(): Int? {
        return nominal
    }

    fun setNominal(nominal: Int?) {
        this.nominal = nominal
    }

    fun getFotoDokumen(): String? {
        return fotoDokumen
    }

    fun setFotoDokumen(fotoDokumen: String) {
        this.fotoDokumen = fotoDokumen
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