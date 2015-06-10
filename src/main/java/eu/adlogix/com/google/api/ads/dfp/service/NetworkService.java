package eu.adlogix.com.google.api.ads.dfp.service;

import static org.joor.Reflect.on;
import lombok.Getter;
import eu.adlogix.com.google.api.ads.dfp.domain.Network;

public class NetworkService<T> {

	@Getter
	T networkServiceInterface;

	public NetworkService(T networkService) {
		this.networkServiceInterface = networkService;
	}

	public <N> Network<N> getCurrentNetwork() {

		try {

			N returnValue = on(networkServiceInterface).call("getCurrentNetwork").get();

			return new Network<N>(returnValue);

		} catch (Exception e) {
			throw new RuntimeException("Error in calling method:getCurrentNetwork in class:"
					+ this.networkServiceInterface.getClass().getName(), e);
		}

	}
}
