#!/bin/bash

aws ses verify-email-identity --email-address idv.demo.mail@gmail.com --region eu-west-1 --endpoint-url=http://localhost:4566
aws ses verify-email-identity --email-address joe.bloggs@hotmail.co.uk --region eu-west-1 --endpoint-url=http://localhost:4566
aws ses verify-email-identity --email-address joebloggs@yahoo.co.uk --region eu-west-1 --endpoint-url=http://localhost:4566