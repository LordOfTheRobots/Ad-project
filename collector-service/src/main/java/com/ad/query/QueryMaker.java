package com.ad.query;

import com.ad.entity.APIKey;
import com.ad.apiparams.Params;

public interface QueryMaker {
    Params makeQuery(RequestAction action, APIKey apiKey);
}
