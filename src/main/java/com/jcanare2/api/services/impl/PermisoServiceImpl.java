package com.jcanare2.api.services.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.jcanare2.api.entity.Permiso;
import com.jcanare2.api.exceptions.NotFoundException;
import com.jcanare2.api.repository.PermisoRepository;
import com.jcanare2.api.services.IPermisoService;

@Service
public class PermisoServiceImpl implements IPermisoService {

    private static final Logger logger = LoggerFactory.getLogger(PermisoServiceImpl.class);

    private final PermisoRepository permisoRepository;

    public PermisoServiceImpl(PermisoRepository permisoRepository) {
        this.permisoRepository = permisoRepository;
    }

    /**
     * Obtener todos los permisos.
     * @return lista de permisos
     */
    @Override
    public List<Permiso> getAllPermisos() {
        logger.info("Consultando todos los permisos");
        return permisoRepository.findAll();
    }

    /**
     * Obtener permiso por id o lanzar excepción si no existe.
     * @param idPermiso identificador del permiso
     * @return Permiso encontrado
     */
    @Override
    public Permiso getById(Long idPermiso) {
        logger.info("Consultando permiso con id {}", idPermiso);
        return permisoRepository.findById(idPermiso)
            .orElseThrow(() -> {
                String msg = "Permiso no encontrado con id: " + idPermiso;
                logger.error(msg);
                throw new NotFoundException(msg);
            });
    }

    /**
     * Crear un nuevo permiso tras validación básica.
     * @param permiso el permiso a crear
     * @return Permiso creado y persistido
     */
    @Override
    @Transactional
    public Permiso create(Permiso permiso) {
        Assert.notNull(permiso, "El permiso no puede ser nulo");
        Assert.hasText(permiso.getNombrePermiso(), "El nombre del permiso no puede estar vacío");
        logger.info("Creando permiso con nombre {}", permiso.getNombrePermiso());
        return permisoRepository.save(permiso);
    }

    /**
     * Actualiza un permiso existente con nuevos datos tras validación.
     * @param permisoRequest datos para actualizar
     * @param idPermiso id del permiso a actualizar
     * @return Permiso actualizado
     */
    @Override
    @Transactional
    public Permiso update(Permiso permisoRequest, Long idPermiso) {
        Assert.notNull(permisoRequest, "El permiso no puede ser nulo");
        Assert.hasText(permisoRequest.getNombrePermiso(), "El nombre del permiso no puede estar vacío");
        logger.info("Actualizando permiso id {}", idPermiso);

        Permiso permiso = permisoRepository.findById(idPermiso)
                .orElseThrow(() -> new NotFoundException("Permiso no encontrado con id: " + idPermiso));

        permiso.setNombrePermiso(permisoRequest.getNombrePermiso());

        return permisoRepository.save(permiso);
    }

    /**
     * Borra un permiso existente por su id.
     * @param id identificador del permiso a borrar
     */
    @Override
    @Transactional
    public void delete(Long id) {
        logger.info("Eliminando permiso con id {}", id);
        boolean exists = permisoRepository.existsById(id);
        if (!exists) {
            throw new NotFoundException("Permiso no encontrado con id: " + id);
        }
        permisoRepository.deleteById(id);
    }
}