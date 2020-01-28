package pl.mdj.rejestrbiurowy.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mdj.rejestrbiurowy.service.interfaces.ClientService;

@Service
@Transactional(readOnly = true)
public class ClientServiceImpl implements ClientService {
}
