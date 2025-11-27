package com.alalay.backend.records;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.UUID;

@Data
@AllArgsConstructor
public class IncidentDTO {
    private UUID id;
    private UUID calamityId;
    private UUID rescuerId;
    private Double latitude;
    private Double longitude;
    private String description;
    private String otherAffectedMembers;
    private String otherImportantDetails;
    private String createdDatetime;
}
