package com.sphtech.data.networking;


public final class ApiEndPoint {
    private static final String BASE_URL = "https://data.gov.sg";


    static final int QUERY_PARAM_LIMIT = 59;

    public static final String END_POINT_GET_MOBILE_DATA = BASE_URL + "/api/action/datastore_search?resource_id=a807b7ab-6cad-4aa6-87d0-e283a7353a0f&limit="+QUERY_PARAM_LIMIT;
}
