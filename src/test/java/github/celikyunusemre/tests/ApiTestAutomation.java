package github.celikyunusemre.tests;

import github.celikyunusemre.base.BaseApiTest;
import github.celikyunusemre.methods.ApiTestMethods;
import github.celikyunusemre.models.CommonModel;
import github.celikyunusemre.models.PetModel;
import io.restassured.response.Response;
import org.junit.Assert;
import org.testng.annotations.Test;

import java.util.*;

public class ApiTestAutomation extends BaseApiTest {
    private String url = "https://petstore.swagger.io/v2/pet";
    private ApiTestMethods methods;

    public ApiTestAutomation() {
        methods = new ApiTestMethods();
    }

    private Map<String, Object> getHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        return headers;
    }
    private Map<String, Object> getHeaders(String accept, String contentType) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("Accept", accept);
        headers.put("Content-Type", contentType);
        return headers;
    }
    @Test(description = "Add a new pet to store")
    public void addNewPet() {
        CommonModel categoryModel = CommonModel.builder()
                .id(1)
                .name("Test Category")
                .build();

        PetModel body = PetModel.builder()
                .id(1)
                .category(categoryModel)
                .name("Rose")
                .photoUrls(getPhotoUrls())
                .tags(getTags())
                .status("available")
                .build();

        Response response = methods.postRequest(url, body, getHeaders());
        Assert.assertEquals(
                200,
                response.statusCode()
        );
    }

    @Test(description = "Get 405 while adding a new pet to store")
    public void failAddingNewPet() {
        Response response = methods.getRequest(url, getHeaders());
        Assert.assertEquals(
                405,
                response.statusCode()
        );
    }

    @Test(description = "Find pet by status")
    public void findPetByStatus() {
        String newUrl = url + "/findByStatus?status=available";
        Response response = methods.getRequest(newUrl, getHeaders());
        Assert.assertEquals(
                200,
                response.statusCode()
        );
    }

    @Test(description = "Find pet by ID")
    public void findPetByID() {
        String newUrl = url + "/1";
        Response response = methods.getRequest(newUrl, getHeaders());
        Assert.assertEquals(
                200,
                response.statusCode()
        );
    }

    @Test(description = "Update an existing pet")
    public void updateExistingPet() {
        CommonModel categoryModel = CommonModel.builder()
                .id(1)
                .name("Test Category")
                .build();

        PetModel body = PetModel.builder()
                .id(1)
                .category(categoryModel)
                .name("Rose")
                .photoUrls(getPhotoUrls())
                .tags(getTags())
                .status("available")
                .build();

        Response response = methods.putRequest(url, body, getHeaders());
        Assert.assertEquals(
                200,
                response.statusCode()
        );
    }

    @Test(description = "Updates a pet in the store with form data")
    public void updatePet() {
        String newUrl = url + "/1";
        String requestBody = "name=Asdfgh&status=sold";
        Response response = methods.postRequest(
                newUrl,
                requestBody,
                getHeaders("application/json", "application/x-www-form-urlencoded"));

        Assert.assertEquals(
                200,
                response.statusCode()
        );
    }

    @Test(description = "Update an existing pet - fail 400")
    public void updateExistingPetFail() {
        CommonModel categoryModel = CommonModel.builder()
                .id(1)
                .name("Test Category")
                .build();

        PetModel body = PetModel.builder()
                .id(345678903)
                .category(categoryModel)
                .name("Rose")
                .photoUrls(getPhotoUrls())
                .tags(getTags())
                .status("available")
                .build();

        Response response = methods.putRequest(url, body, getHeaders());
        Assert.assertEquals(
                400,
                response.statusCode()
        );
    }

    @Test(description = "Try to find non-existing pet by ID")
    public void findPetNonExistByID() {
        String newUrl = url + "/556677";
        Response response = methods.getRequest(newUrl, getHeaders());
        Assert.assertEquals(
                404,
                response.statusCode()
        );
    }

    @Test(description = "Deletes a pet")
    public void deletePet() {
        String newUrl = url + "/1";
        Response response = methods.deleteRequest(newUrl, getHeaders());
        Assert.assertEquals(
                200,
                response.statusCode()
        );
    }

    @Test(description = "Deletes a pet - fail 404")
    public void deletePetFail() {
        String newUrl = url + "/127461231";
        Response response = methods.deleteRequest(newUrl, getHeaders());
        Assert.assertEquals(
                404,
                response.statusCode()
        );
    }

    private List<String> getPhotoUrls() {
        List<String> urls = new ArrayList<>();
        urls.add("https://w7.pngwing.com/pngs/502/150/png-transparent-havanese-dog-pet-sitting-labrador-retriever" +
                "-puppy-cat-pet-dog-animals-carnivoran-pet.png");
        return urls;
    }

    private List<CommonModel> getTags() {
        List<CommonModel> tags = new ArrayList<>();
        CommonModel tag = CommonModel.builder()
                .id(1)
                .name("Test Tag")
                .build();
        tags.add(tag);
        return tags;
    }

}
