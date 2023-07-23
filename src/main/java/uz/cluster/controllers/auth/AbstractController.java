package uz.cluster.controllers.auth;

import lombok.RequiredArgsConstructor;
import uz.cluster.services.auth_service.AbstractService;

@RequiredArgsConstructor
public abstract class AbstractController<S extends AbstractService> {
    protected final S service;
    protected final String API = "/api";
    protected final String VERSION = "/auth";
    protected final String PATH = API + VERSION;
}
