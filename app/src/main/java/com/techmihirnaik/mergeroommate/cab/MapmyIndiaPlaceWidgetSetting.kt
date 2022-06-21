package com.techmihirnaik.mergeroommate.cab

import com.mapmyindia.sdk.geojson.Point
import com.mapmyindia.sdk.plugins.places.autocomplete.model.PlaceOptions
import com.techmihirnaik.mergeroommate.R

class MapmyIndiaPlaceWidgetSetting {
    var isDefault = true
    var signatureVertical = PlaceOptions.GRAVITY_TOP
    var signatureHorizontal = PlaceOptions.GRAVITY_LEFT
    var logoSize = PlaceOptions.SIZE_MEDIUM
    var location: Point? = null
    var filter: String? = null
    var isEnableHistory = false
    var pod: String? = null
    var hint = "Search Here"
    var isEnableTextSearch = false
    var backgroundColor = R.color.white
    var toolbarColor = R.color.white
    var isBridgeEnable = false
    var isHyperLocalEnable = false

    companion object {
        val instance = MapmyIndiaPlaceWidgetSetting()
    }

}