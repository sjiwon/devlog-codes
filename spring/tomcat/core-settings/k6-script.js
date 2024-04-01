import http from 'k6/http';
import {check} from 'k6';

export const options = {
    scenarios: {
        spike: {
            executor: 'constant-vus', // sends VUs at a constant number.
            vus: 5000, // virtual users
            duration: '1s', //
            gracefulStop: '5m' // Time to wait for iterations to finish executing before stopping them forcefully.
        },
    },
};

export default function () {
    const res = http.get('http://{IP}:18080/api');
    check(res, {'check status 200': (r) => r.status === 200});
};
