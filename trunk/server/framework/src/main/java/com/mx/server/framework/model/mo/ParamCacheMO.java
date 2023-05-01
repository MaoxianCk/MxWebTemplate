package com.mx.server.framework.model.mo;

import com.mx.server.framework.model.entity.ParamEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ParamCacheMO {
    private Long id;
    private String name;
    private String code;
    private String value;
    private String description;

    public ParamCacheMO(ParamEntity h) {
        this.id = h.getId();
        this.name = h.getName();
        this.code = h.getCode();
        this.value = h.getValue();
        this.description = h.getDescription();
    }
}
