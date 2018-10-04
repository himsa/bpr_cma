package com.layanacomputindo.bprcma.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class HargaTanah {
    @SerializedName("id")
    @Expose
    private var id: Int? = null
    @SerializedName("faktor_penilaian_id")
    @Expose
    private var faktorPenilaianId: Int? = null
    @SerializedName("kantor_pemasaran")
    @Expose
    private var kantorPemasaran: Int? = null
    @SerializedName("kantor_notaris")
    @Expose
    private var kantorNotaris: Int? = null
    @SerializedName("masyarakat_sekitar")
    @Expose
    private var masyarakatSekitar: Int? = null
    @SerializedName("njop_pbb_terakhir")
    @Expose
    private var njopPbbTerakhir: Int? = null
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

    fun getKantorPemasaran(): Int? {
        return kantorPemasaran
    }

    fun setKantorPemasaran(kantorPemasaran: Int?) {
        this.kantorPemasaran = kantorPemasaran
    }

    fun getKantorNotaris(): Int? {
        return kantorNotaris
    }

    fun setKantorNotaris(kantorNotaris: Int?) {
        this.kantorNotaris = kantorNotaris
    }

    fun getMasyarakatSekitar(): Int? {
        return masyarakatSekitar
    }

    fun setMasyarakatSekitar(masyarakatSekitar: Int?) {
        this.masyarakatSekitar = masyarakatSekitar
    }

    fun getNjopPbbTerakhir(): Int? {
        return njopPbbTerakhir
    }

    fun setNjopPbbTerakhir(njopPbbTerakhir: Int?) {
        this.njopPbbTerakhir = njopPbbTerakhir
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