IMAGE=tgkaiser/financ
VERSION=1.5.5

build:
	docker buildx build --platform linux/amd64 -t $(IMAGE):$(VERSION) .

push:
	docker push $(IMAGE):$(VERSION)

deploy: build push
