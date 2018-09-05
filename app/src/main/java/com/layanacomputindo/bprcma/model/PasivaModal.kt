package com.layanacomputindo.bprcma.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class PasivaModal {
    @SerializedName("id")
    @Expose
    private var id: Int? = null
    @SerializedName("analisa_keuangan_id")
    @Expose
    private var analisaKeuanganId: Int? = null
    @SerializedName("modal")
    @Expose
    private var modal: Int? = null
    @SerializedName("modal_kerja")
    @Expose
    private var modalKerja: Int? = null
    @SerializedName("cadangan_laba_ditahan")
    @Expose
    private var cadanganLabaDitahan: Int? = null
    @SerializedName("laba_tahun_berjalan")
    @Expose
    private var labaTahunBerjalan: Int? = null
    @SerializedName("sub_jumlah")
    @Expose
    private var subJumlah: Int? = null
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

    fun getAnalisaKeuanganId(): Int? {
        return analisaKeuanganId
    }

    fun setAnalisaKeuanganId(analisaKeuanganId: Int?) {
        this.analisaKeuanganId = analisaKeuanganId
    }

    fun getModal(): Int? {
        return modal
    }

    fun setModal(modal: Int?) {
        this.modal = modal
    }

    fun getModalKerja(): Int? {
        return modalKerja
    }

    fun setModalKerja(modalKerja: Int?) {
        this.modalKerja = modalKerja
    }

    fun getCadanganLabaDitahan(): Int? {
        return cadanganLabaDitahan
    }

    fun setCadanganLabaDitahan(cadanganLabaDitahan: Int?) {
        this.cadanganLabaDitahan = cadanganLabaDitahan
    }

    fun getLabaTahunBerjalan(): Int? {
        return labaTahunBerjalan
    }

    fun setLabaTahunBerjalan(labaTahunBerjalan: Int?) {
        this.labaTahunBerjalan = labaTahunBerjalan
    }

    fun getSubJumlah(): Int? {
        return subJumlah
    }

    fun setSubJumlah(subJumlah: Int?) {
        this.subJumlah = subJumlah
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