# Spring Boot gRPC Playground

A small multi-module Spring Boot and Kotlin playground that demonstrates service-to-service communication over gRPC.

`service-one` exposes an HTTP endpoint and calls `service-two` through a generated gRPC client. `service-two` implements the gRPC server defined in the shared `grpc-contract` module.

## Modules

| Module | Purpose |
| --- | --- |
| `grpc-contract` | Owns the protobuf contract and generated Java gRPC types for `ProfileService`. |
| `service-one` | Spring Boot WebMVC application exposing `POST /lookups`; calls `service-two` with a blocking gRPC stub. |
| `service-two` | Spring Boot gRPC server implementing `ProfileService/GetProfile`. |

## Requirements

- JDK 25
- Gradle wrapper included in the repository

The CI workflow uses Zulu JDK `25.0.3` and Gradle `9.5.1`.

## Build And Test

On Windows:

```powershell
.\gradlew.bat build
```

On macOS or Linux:

```bash
./gradlew build
```

The main integration test starts `service-two` on a random gRPC port, configures `service-one` to use that port, and verifies that `POST /lookups` returns data retrieved over gRPC.

## Run Locally

Run both services in separate terminals.

Start `service-two`, the gRPC server:

```powershell
.\gradlew.bat :service-two:bootRun
```

Start `service-one`, the HTTP API:

```powershell
.\gradlew.bat :service-one:bootRun
```

Default ports:

| Service | Port | Protocol |
| --- | ---: | --- |
| `service-one` | `8080` | HTTP |
| `service-two` | `9090` | gRPC |

`service-one` is configured to call `service-two` at `static://localhost:9090`.

You can also start both applications with Gradle parallel execution:

```powershell
.\gradlew.bat bootRunAll --parallel
```

## Try The Lookup Endpoint

With both services running:

```powershell
Invoke-RestMethod `
  -Method Post `
  -Uri http://localhost:8080/lookups `
  -ContentType 'application/json' `
  -Body '{"subject":"spring"}'
```

Expected response:

```json
{
  "subject": "spring",
  "profileId": "profile-spring",
  "displayName": "Spring Profile",
  "source": "service-two-grpc"
}
```

Equivalent `curl` request:

```bash
curl -X POST http://localhost:8080/lookups \
  -H 'Content-Type: application/json' \
  -d '{"subject":"spring"}'
```

## Contract

The gRPC contract lives in [`grpc-contract/src/main/proto/profile.proto`](grpc-contract/src/main/proto/profile.proto):

```proto
service ProfileService {
  rpc GetProfile(ProfileRequest) returns (ProfileResponse);
}
```

Generated classes are published by `grpc-contract` and consumed by both services.

## Useful Commands

```powershell
# Build all modules
.\gradlew.bat build

# Run the HTTP service only
.\gradlew.bat :service-one:bootRun

# Run the gRPC service only
.\gradlew.bat :service-two:bootRun

# Run both services together
.\gradlew.bat bootRunAll --parallel
```
