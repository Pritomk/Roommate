package com.techmihirnaik.mergeroommate.model;

@Entity
data class ImageTest {

    @PrimaryKey(autoGenerate = true)

    var id: Int = 1

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    var data: ByteArray? = null
}