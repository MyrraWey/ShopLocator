package com.muravyovdmitr.shoplocator.network;

import com.muravyovdmitr.shoplocator.data.Owner;
import com.muravyovdmitr.shoplocator.data.Shop;

import java.util.UUID;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Dima Muravyov on 22.05.2016.
 */
public class Services {
    public interface OwnerService {
        @GET("Owners")
        Call<BackendlessListResponse<Owner>> getOwners();

        @GET("Owners/{ownerId}")
        Call<Owner> getOwner(@Path("ownerId") UUID ownerId);

        @POST("Owners")
        Call<Owner> createOwner(@Body Owner owner);

        @PUT("Owners/{ownerId}")
        Call<Owner> updateOwner(
                @Path("ownerId") UUID ownerId,
                @Body Owner owner
        );

        @DELETE("Owners/{ownerId}")
        Call<Owner> deleteOwner(@Path("ownerId") UUID ownerId);

        @GET("Owners")
        Call<BackendlessListResponse<Owner>> getOwnerByQuery(@Query("where") String ownerId);
    }

    public interface ShopService {
        @GET("Shops")
        Call<BackendlessListResponse<Shop>> getShops();

        @GET("Shops/{shopId}")
        Call<Shop> getShop(@Path("shopId") UUID shopId);

        @POST("Shops")
        Call<Shop> createShop(@Body Shop shop);

        @PUT("Shops/{shopId}")
        Call<Shop> updateShop(
                @Path("shopId") UUID shopId,
                @Body Shop shop
        );

        @DELETE("Shops/{shopId}")
        Call<Shop> deleteShop(@Path("shopId") UUID shopId);

        @GET("Shops")
        Call<BackendlessListResponse<Shop>> getShopByQuery(@Query("where") String shopId);
    }
}
