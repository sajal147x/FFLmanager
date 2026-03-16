package com.fflmanager.events.service;


import com.fflmanager.FFLmanagerApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
/**
 * Author: Sajal Gupta
 * Date: 3/16/26 3:42 PM
 */
@Service
public class SupabaseStorage implements CdnStorage {

    @Value("${supabase.url}")
    private String SUPABASE_URL;

    @Value("${supabase.key}")
    private String SUPABASE_KEY;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public String uploadImageToCdnStorageFromUrl(String url, String fileName) {
        try {
            // STEP 1: download image bytes from url
            byte[] imageBytes = restTemplate.getForObject(url, byte[].class);

            // STEP 2: upload to supabase bucket
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + SUPABASE_KEY);
            headers.setContentType(MediaType.IMAGE_JPEG);

            HttpEntity<byte[]> request = new HttpEntity<>(imageBytes, headers);

            String uploadUrl = SUPABASE_URL + "/storage/v1/object/fighters/" + fileName;
            restTemplate.exchange(uploadUrl, HttpMethod.POST, request, String.class);

            // STEP 3: return public url
            return SUPABASE_URL + "/storage/v1/object/public/fighters/" + fileName;

        } catch (Exception e) {
            System.out.println("ERROR UPLOADING IMAGE: " + fileName);
            e.printStackTrace();
            return null;
        }
    }


    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(FFLmanagerApplication.class, args);
        SupabaseStorage storage = context.getBean(SupabaseStorage.class);
        storage.uploadImageToCdnStorageFromUrl("https://images.tapology.com/headshot_images/85897/large/Movsar_Evloev-hs.jpg?1552573825", "test.jpg");
    }
}