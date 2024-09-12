package com.ispark.upper_package_service.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Data
@Document(collection = "upperPackages")
public class UpperPackage {

    @Id
    private String upperPackageId;                  // Üst paketin benzersiz ID'si
    private String upperPackageName;                // Üst paketin adı
    private String upperPackageDescription;         // Üst paketin açıklaması
    private String upperPackageNo;                  // Üst paketin benzersiz numarası
    private List<SubPackageInfo> upperSubPackages;  // Alt paket bilgilerini içeren liste

    @Data
    public static class SubPackageInfo {
        private String subPackageId;
        private String subPackageName;
        private String subPackageDescription;
    }
}
