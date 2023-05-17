package ml.bigbrains.proxymnp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.bigbrains.proxymnp.model.MNPData;
import ml.bigbrains.proxymnp.service.NiirClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class MainController {

    private NiirClient client;

    @CrossOrigin
    @GetMapping("/mnp/{number}")
    @Operation(
            summary = "get MNP data",
            description = "get data about MNP (Mobile Number Portability) for number from request"
    )
    @ApiResponses(value = {@ApiResponse(description = "return NPMData object or NULL")})
    public ResponseEntity<MNPData> getMNP(@PathVariable String number) throws Exception
    {
        log.debug("request number: {}",number);
        MNPData data = client.getMNPData(number);
        if(data==null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
