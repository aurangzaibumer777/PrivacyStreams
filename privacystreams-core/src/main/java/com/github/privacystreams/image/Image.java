package com.github.privacystreams.image;

import android.net.Uri;

import com.github.privacystreams.core.Function;
import com.github.privacystreams.core.Item;
import com.github.privacystreams.core.providers.MultiItemStreamProvider;
import com.github.privacystreams.core.providers.SingleItemStreamProvider;

import java.util.Map;

/**
 * An Image item represents an image file.
 */

public class Image extends Item {

    // type: Long
    private static final String TIMESTAMP = "timestamp";

    // type: String, representing the URI of photo file
    public static final String URI = "uri";

    // TODO create a metadata field, put lat and lng to metadata
    private static final String LAT = "lat";
    private static final String LNG = "lng";

    Image(String timestamp, Uri file_uri, double lat, double lng) {
        this.setFieldValue(TIMESTAMP, timestamp);
        this.setFieldValue(URI, file_uri.toString());
        this.setFieldValue(LAT,lat);
        this.setFieldValue(LNG,lng);
    }

    /**
     * get a item provider that takes a photo using camera
     * the photo item represents the taken photo
     * @return the provider
     */
    public static SingleItemStreamProvider takeFromCamera() {
        // TODO implement this
        return null;
    }

    /**
     * get a stream provider that provides the photo items from file system
     * each photo item represents a photo in storage
     * @return the provider
     */
    public static MultiItemStreamProvider readFromStorage() {
        return new ImageStorageProvider();
    }

}