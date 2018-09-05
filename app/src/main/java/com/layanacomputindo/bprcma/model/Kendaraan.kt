package com.layanacomputindo.bprcma.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class Kendaraan {
    @SerializedName("id")
    @Expose
    private var id: Int? = null
    @SerializedName("tanggal_pemeriksaan")
    @Expose
    private var tanggalPemeriksaan: String? = null
    @SerializedName("jenis_kendaraan")
    @Expose
    private var jenisKendaraan: String? = null
    @SerializedName("penggunaan_jaminan")
    @Expose
    private var penggunaanJaminan: String? = null
    @SerializedName("daerah_operasional_jaminan")
    @Expose
    private var daerahOperasionalJaminan: Int? = null
    @SerializedName("nama_pemilik_bpkb")
    @Expose
    private var namaPemilikBpkb: String? = null
    @SerializedName("nama_pemilik")
    @Expose
    private var namaPemilik: String? = null
    @SerializedName("hubungan_pemilik_debitur")
    @Expose
    private var hubunganPemilikDebitur: String? = null
    @SerializedName("nomor_faktur")
    @Expose
    private var nomorFaktur: String? = null
    @SerializedName("nomor_mesin")
    @Expose
    private var nomorMesin: String? = null
    @SerializedName("bukti_gesek_mesin")
    @Expose
    private var buktiGesekMesin: Int? = null
    @SerializedName("nomor_rangka")
    @Expose
    private var nomorRangka: String? = null
    @SerializedName("bukti_gesek_rangka")
    @Expose
    private var buktiGesekRangka: Int? = null
    @SerializedName("nomor_polisi")
    @Expose
    private var nomorPolisi: String? = null
    @SerializedName("nomor_stnk")
    @Expose
    private var nomorStnk: String? = null
    @SerializedName("nomor_bpkb")
    @Expose
    private var nomorBpkb: String? = null
    @SerializedName("warna")
    @Expose
    private var warna: String? = null
    @SerializedName("tahun_pembuatan")
    @Expose
    private var tahunPembuatan: Int? = null
    @SerializedName("merk_kendaraan")
    @Expose
    private var merkKendaraan: String? = null
    @SerializedName("tipe_model_kendaraan")
    @Expose
    private var tipeModelKendaraan: String? = null
    @SerializedName("check_samsat")
    @Expose
    private var checkSamsat: String? = null
    @SerializedName("nama_petugas_samsat")
    @Expose
    private var namaPetugasSamsat: String? = null
    @SerializedName("nomor_telepon_samsat")
    @Expose
    private var nomorTeleponSamsat: String? = null
    @SerializedName("hasil_kesesuaian")
    @Expose
    private var hasilKesesuaian: Int? = null
    @SerializedName("keterangan_lain")
    @Expose
    private var keteranganLain: String? = null
    @SerializedName("nilai_market")
    @Expose
    private var nilaiMarket: Int? = null
    @SerializedName("nilai_likuidasi")
    @Expose
    private var nilaiLikuidasi: Int? = null
    @SerializedName("nama_informan_harga")
    @Expose
    private var namaInformanHarga: String? = null
    @SerializedName("pekerjaan_informan_harga")
    @Expose
    private var pekerjaanInformanHarga: String? = null
    @SerializedName("telepon_informan_harga")
    @Expose
    private var teleponInformanHarga: String? = null
    @SerializedName("alamat_informan_harga")
    @Expose
    private var alamatInformanHarga: String? = null
    @SerializedName("pendapat_pemeriksa")
    @Expose
    private var pendapatPemeriksa: String? = null
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

    fun getTanggalPemeriksaan(): String? {
        return tanggalPemeriksaan
    }

    fun setTanggalPemeriksaan(tanggalPemeriksaan: String) {
        this.tanggalPemeriksaan = tanggalPemeriksaan
    }

    fun getJenisKendaraan(): String? {
        return jenisKendaraan
    }

    fun setJenisKendaraan(jenisKendaraan: String) {
        this.jenisKendaraan = jenisKendaraan
    }

    fun getPenggunaanJaminan(): String? {
        return penggunaanJaminan
    }

    fun setPenggunaanJaminan(penggunaanJaminan: String) {
        this.penggunaanJaminan = penggunaanJaminan
    }

    fun getDaerahOperasionalJaminan(): Int? {
        return daerahOperasionalJaminan
    }

    fun setDaerahOperasionalJaminan(daerahOperasionalJaminan: Int?) {
        this.daerahOperasionalJaminan = daerahOperasionalJaminan
    }

    fun getNamaPemilikBpkb(): String? {
        return namaPemilikBpkb
    }

    fun setNamaPemilikBpkb(namaPemilikBpkb: String) {
        this.namaPemilikBpkb = namaPemilikBpkb
    }

    fun getNamaPemilik(): String? {
        return namaPemilik
    }

    fun setNamaPemilik(namaPemilik: String) {
        this.namaPemilik = namaPemilik
    }

    fun getHubunganPemilikDebitur(): String? {
        return hubunganPemilikDebitur
    }

    fun setHubunganPemilikDebitur(hubunganPemilikDebitur: String) {
        this.hubunganPemilikDebitur = hubunganPemilikDebitur
    }

    fun getNomorFaktur(): String? {
        return nomorFaktur
    }

    fun setNomorFaktur(nomorFaktur: String) {
        this.nomorFaktur = nomorFaktur
    }

    fun getNomorMesin(): String? {
        return nomorMesin
    }

    fun setNomorMesin(nomorMesin: String) {
        this.nomorMesin = nomorMesin
    }

    fun getBuktiGesekMesin(): Int? {
        return buktiGesekMesin
    }

    fun setBuktiGesekMesin(buktiGesekMesin: Int?) {
        this.buktiGesekMesin = buktiGesekMesin
    }

    fun getNomorRangka(): String? {
        return nomorRangka
    }

    fun setNomorRangka(nomorRangka: String) {
        this.nomorRangka = nomorRangka
    }

    fun getBuktiGesekRangka(): Int? {
        return buktiGesekRangka
    }

    fun setBuktiGesekRangka(buktiGesekRangka: Int?) {
        this.buktiGesekRangka = buktiGesekRangka
    }

    fun getNomorPolisi(): String? {
        return nomorPolisi
    }

    fun setNomorPolisi(nomorPolisi: String) {
        this.nomorPolisi = nomorPolisi
    }

    fun getNomorStnk(): String? {
        return nomorStnk
    }

    fun setNomorStnk(nomorStnk: String) {
        this.nomorStnk = nomorStnk
    }

    fun getNomorBpkb(): String? {
        return nomorBpkb
    }

    fun setNomorBpkb(nomorBpkb: String) {
        this.nomorBpkb = nomorBpkb
    }

    fun getWarna(): String? {
        return warna
    }

    fun setWarna(warna: String) {
        this.warna = warna
    }

    fun getTahunPembuatan(): Int? {
        return tahunPembuatan
    }

    fun setTahunPembuatan(tahunPembuatan: Int?) {
        this.tahunPembuatan = tahunPembuatan
    }

    fun getMerkKendaraan(): String? {
        return merkKendaraan
    }

    fun setMerkKendaraan(merkKendaraan: String) {
        this.merkKendaraan = merkKendaraan
    }

    fun getTipeModelKendaraan(): String? {
        return tipeModelKendaraan
    }

    fun setTipeModelKendaraan(tipeModelKendaraan: String) {
        this.tipeModelKendaraan = tipeModelKendaraan
    }

    fun getCheckSamsat(): String? {
        return checkSamsat
    }

    fun setCheckSamsat(checkSamsat: String) {
        this.checkSamsat = checkSamsat
    }

    fun getNamaPetugasSamsat(): String? {
        return namaPetugasSamsat
    }

    fun setNamaPetugasSamsat(namaPetugasSamsat: String) {
        this.namaPetugasSamsat = namaPetugasSamsat
    }

    fun getNomorTeleponSamsat(): String? {
        return nomorTeleponSamsat
    }

    fun setNomorTeleponSamsat(nomorTeleponSamsat: String) {
        this.nomorTeleponSamsat = nomorTeleponSamsat
    }

    fun getHasilKesesuaian(): Int? {
        return hasilKesesuaian
    }

    fun setHasilKesesuaian(hasilKesesuaian: Int?) {
        this.hasilKesesuaian = hasilKesesuaian
    }

    fun getKeteranganLain(): String? {
        return keteranganLain
    }

    fun setKeteranganLain(keteranganLain: String) {
        this.keteranganLain = keteranganLain
    }

    fun getNilaiMarket(): Int? {
        return nilaiMarket
    }

    fun setNilaiMarket(nilaiMarket: Int?) {
        this.nilaiMarket = nilaiMarket
    }

    fun getNilaiLikuidasi(): Int? {
        return nilaiLikuidasi
    }

    fun setNilaiLikuidasi(nilaiLikuidasi: Int?) {
        this.nilaiLikuidasi = nilaiLikuidasi
    }

    fun getNamaInformanHarga(): String? {
        return namaInformanHarga
    }

    fun setNamaInformanHarga(namaInformanHarga: String) {
        this.namaInformanHarga = namaInformanHarga
    }

    fun getPekerjaanInformanHarga(): String? {
        return pekerjaanInformanHarga
    }

    fun setPekerjaanInformanHarga(pekerjaanInformanHarga: String) {
        this.pekerjaanInformanHarga = pekerjaanInformanHarga
    }

    fun getTeleponInformanHarga(): String? {
        return teleponInformanHarga
    }

    fun setTeleponInformanHarga(teleponInformanHarga: String) {
        this.teleponInformanHarga = teleponInformanHarga
    }

    fun getAlamatInformanHarga(): String? {
        return alamatInformanHarga
    }

    fun setAlamatInformanHarga(alamatInformanHarga: String) {
        this.alamatInformanHarga = alamatInformanHarga
    }

    fun getPendapatPemeriksa(): String? {
        return pendapatPemeriksa
    }

    fun setPendapatPemeriksa(pendapatPemeriksa: String) {
        this.pendapatPemeriksa = pendapatPemeriksa
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