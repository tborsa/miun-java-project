package r.orderapplication.orderRest;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by Alex on 2017-03-01.
 */

public class OrderClient {
    private OrderStatusListener osl;
    private OrderRest client;

    public OrderClient(String dataBaseURL) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(dataBaseURL)
                .client(httpClient.build()).addConverterFactory(SimpleXmlConverterFactory.create());

        Retrofit retrofit = builder.build();

        client = retrofit.create(OrderRest.class);
    }

    public void setStatusListener(OrderStatusListener osl) {
        this.osl = osl;
    }

    public void fetchOrderList() {
        Call<OrderEntities> call = client.selectOrder();
        call.enqueue(new Callback<OrderEntities>() {
            @Override
            public void onResponse(Call<OrderEntities> call, Response<OrderEntities> response) {
                OrderEntities ordrar = response.body();
                osl.orderListRecived(ordrar);
                System.out.println("onResponse Lyckat!");
            }

            @Override
            public void onFailure(Call<OrderEntities> call, Throwable t) {
                System.out.println("onResponse Misslyckat!");
            }
        });
    }

    public void postOrder(OrderEntity orderEntity) {
        Call<OrderEntity> call = client.createOrder(orderEntity);
        call.enqueue(new Callback<OrderEntity>() {
            @Override
            public void onResponse(Call<OrderEntity> call, Response<OrderEntity> response) {
                int resp_orig = response.code();
                int resp = resp_orig / 100;

                if (resp != 2) {
                    throw new RuntimeException(resp_orig + ": Fel kod. Inte 200.");
                }
            }

            @Override
            public void onFailure(Call<OrderEntity> call, Throwable t) {

            }
        });
    }

    public void updateOrder(OrderEntity orderEntity) {
        Call<OrderEntity> call = client.updateOrder(orderEntity, orderEntity.getId());
        call.enqueue(new Callback<OrderEntity>() {
            @Override
            public void onResponse(Call<OrderEntity> call, Response<OrderEntity> response) {
                int resp_orig = response.code();
                int resp = resp_orig / 100;

                if (resp != 2) {
                    throw new RuntimeException(resp_orig + ": Fel kod. Inte 200.");
                }
            }

            @Override
            public void onFailure(Call<OrderEntity> call, Throwable t) {

            }
        });
    }

    public void deleteOrder(OrderEntity orderEntity) {
        Call<OrderEntity> call = client.deleteOrder(orderEntity.getId());
        call.enqueue(new Callback<OrderEntity>() {
            @Override
            public void onResponse(Call<OrderEntity> call, Response<OrderEntity> response) {
                int resp_orig = response.code();
                int resp = resp_orig / 100;

                if (resp != 2) {
                    throw new RuntimeException(resp_orig + ": Fel kod. Inte 200.");
                }
            }

            @Override
            public void onFailure(Call<OrderEntity> call, Throwable t) {

            }
        });
    }
}
