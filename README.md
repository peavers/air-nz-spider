# Air NZ Spider

Since flights to Melbourne are now blocked until the 25th of Sept, but Air NZ is releasing flights randomly before then
keep an eye on those dates and post to Slack as soon as we see any.

## Running

Make use of a docker-compose.yml file:

```yaml
version: "3"
services:
  air-nz-spider:
    image: peavers/air-nz-spider:latest
    container_name: air-nz-spider
    restart: unless-stopped
    environment:
      - SPIDER_NOTIFICATION_SLACK_WEBHOOK=""
    logging:
      options:
        max-size: "2m"
        max-file: "5"
```

Just remember to add the webhook for notifications. 