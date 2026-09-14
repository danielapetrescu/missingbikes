# Deployment notes

Notes about things that work locally but must be changed before the project goes to the cloud.

## Google API keys

Currently the project uses two Google API keys, injected as environment variables
(`GOOGLE_MAPS_API_KEY`, `GOOGLE_PLACES_API_KEY`) and read in `application.properties`
as `google.maps.api-key` and `google.places.api-key`.

### Current state (local development)

The Places key is used server-side from `AddressController`, which calls
`https://places.googleapis.com/v1/places:autocomplete`. A server-side call sends no
`Referer` header, so a key restricted to "Websites (HTTP referrers)" is rejected with
`403 API_KEY_HTTP_REFERRER_BLOCKED`. To unblock local development, the Places key's
**Application restrictions** were set to **None**.

**This is not safe for production**: a key with no application restriction can be used by
anyone who obtains it, and the usage is billed to our project.

### Before deploying

- [ ] Restrict the **Places (backend) key** to the server's public IP address(es), or replace
      the API key with a service account / OAuth token if the API supports it.
- [ ] Restrict the **Maps (browser) key** to HTTP referrers matching the production domain only.
- [ ] Keep the two keys separate. One key cannot satisfy both a browser restriction and a
      server restriction at the same time - that is exactly what caused the 403 above.
- [ ] Keep **API restrictions** narrow on each key: the backend key should allow only the
      Places API, the browser key only the Maps JavaScript API.
- [ ] Never commit key values. `application.properties` is tracked in git and must keep using
      `${ENV_VAR}` placeholders only. In the cloud, provide the values through the platform's
      secret manager or environment configuration, not through a properties file.
- [ ] Set up a billing quota / alert on the GCP project so a leaked key cannot cause unlimited cost.
- [ ] Rotate the key that was temporarily unrestricted during development.
