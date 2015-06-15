package com.example.localdatabase;

import android.net.Uri;
import android.provider.BaseColumns;

public final class Jobs implements BaseColumns{
public static final String COMPANY_NAME = "cname";
public static final String DESCRIPTION = "description";
public static final String CONTENT_TYPE = "vnd.android.cursor.dir/com.example.jobs";
    public static final String JOB_ID = "_id";
    public static final Uri CONTENT_URI = Uri.parse("content://"
            + UserContentProvider.AUTHORITY + "/jobdata");
}
