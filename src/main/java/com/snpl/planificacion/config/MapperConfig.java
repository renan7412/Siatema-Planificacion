package com.snpl.planificacion.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true);
        /*mapper.typeMap(Usuario.class, UsuarioDTO.class).addMappings(m -> {
            m.map(src -> src.getRoles().stream()
                    .map(Rol::getNombre)
                    .collect(Collectors.toSet()), UsuarioDTO::setRoles);
        });*/
        return mapper;
    }
}
