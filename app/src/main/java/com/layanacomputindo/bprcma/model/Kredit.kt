package com.layanacomputindo.bprcma.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class Kredit {
    @SerializedName("id")
    @Expose
    private var id: Int? = null
    @SerializedName("debitur_id")
    @Expose
    private var debiturId: Int? = null
    @SerializedName("jangka_waktu")
    @Expose
    private var jangkaWaktu: Int? = null
    @SerializedName("status_kredit")
    @Expose
    private var statusKredit: String? = null
    @SerializedName("tujuan_kredit")
    @Expose
    private var tujuanKredit: String? = null
    @SerializedName("jenis_kredit")
    @Expose
    private var jenisKredit: String? = null
    @SerializedName("nominal")
    @Expose
    private var nominal: Int? = null
    @SerializedName("sektor_ekonomi")
    @Expose
    private var sektorEkonomi: String? = null
    @SerializedName("spesifikasi_usaha")
    @Expose
    private var spesifikasiUsaha: String? = null
    @SerializedName("detail_tujuan_kredit")
    @Expose
    private var detailTujuanKredit: String? = null
    @SerializedName("sumber_pengembalian")
    @Expose
    private var sumberPengembalian: String? = null
    @SerializedName("detail_sumber_pengembalian")
    @Expose
    private var detailSumberPengembalian: String? = null
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

    fun getJangkaWaktu(): Int? {
        return jangkaWaktu
    }

    fun setJangkaWaktu(jangkaWaktu: Int?) {
        this.jangkaWaktu = jangkaWaktu
    }

    fun getStatusKredit(): String? {
        return statusKredit
    }

    fun setStatusKredit(statusKredit: String) {
        this.statusKredit = statusKredit
    }

    fun getTujuanKredit(): String? {
        return tujuanKredit
    }

    fun setTujuanKredit(tujuanKredit: String) {
        this.tujuanKredit = tujuanKredit
    }

    fun getJenisKredit(): String? {
        return jenisKredit
    }

    fun setJenisKredit(jenisKredit: String) {
        this.jenisKredit = jenisKredit
    }

    fun getNominal(): Int? {
        return nominal
    }

    fun setNominal(nominal: Int?) {
        this.nominal = nominal
    }

    fun getSektorEkonomi(): String? {
        return sektorEkonomi
    }

    fun setSektorEkonomi(sektorEkonomi: String) {
        this.sektorEkonomi = sektorEkonomi
    }

    fun getSpesifikasiUsaha(): String? {
        return spesifikasiUsaha
    }

    fun setSpesifikasiUsaha(spesifikasiUsaha: String) {
        this.spesifikasiUsaha = spesifikasiUsaha
    }

    fun getDetailTujuanKredit(): String? {
        return detailTujuanKredit
    }

    fun setDetailTujuanKredit(detailTujuanKredit: String) {
        this.detailTujuanKredit = detailTujuanKredit
    }

    fun getSumberPengembalian(): String? {
        return sumberPengembalian
    }

    fun setSumberPengembalian(sumberPengembalian: String) {
        this.sumberPengembalian = sumberPengembalian
    }

    fun getDetailSumberPengembalian(): String? {
        return detailSumberPengembalian
    }

    fun setDetailSumberPengembalian(detailSumberPengembalian: String) {
        this.detailSumberPengembalian = detailSumberPengembalian
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