IMAGE=tgkaiser/financ
VERSION=1.7.0

build:
	docker buildx build --platform linux/amd64 -t $(IMAGE):$(VERSION) .

push:
	docker push $(IMAGE):$(VERSION)

deploy: build push
