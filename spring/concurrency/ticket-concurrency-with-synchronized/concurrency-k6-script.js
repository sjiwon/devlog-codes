import http from "k6/http";

export const options = {
    scenarios: {
        spike: {
            executor: "constant-vus",
            vus: 200,
            duration: "1s",
            gracefulStop: "5m"
        },
    },
};

export default function () {
    const data = {
        "amount": 5
    }
    const res = http.post("http://localhost/api/v1/tickets/1/purchase", JSON.stringify(data), {
        headers: {
            "Content-Type": "application/json"
        },
    });
    console.log(res.body);
};
